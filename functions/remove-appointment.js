/*
 * Steps:
 * 1. Verify authenticated userData with verifyIdToken
 * 2. Get authenticated userData's houseNumber
 * 3. Check if appointment is for nextWeek or this week
 * 4. Check if the spot to remove has the same houseNumber
 * 5. Remove the houseNumber from that spot
 * 6. Decrement counter
 */

// request.body: {
//   appointment: {
//     machineInfo: {
//       id: 'A1'
//       room: { id: 'A', name: 'Room A' }
//       type: 'laundrymachine'
//     },
//     day: { index: 0, value: '2018-07-01' }
//     time: { index: 0, value: 'xx-xx' }
//     houseNumber: '230'
//   },
//   jwt: 'jwt'
// }

const admin = require('firebase-admin');
const appUtil = require('./appointment-util');

exports.handler = function (request, response) {
  // console.log('request.body', request.body);
  const appointment = request.body.appointment;
  const jwtToken = request.body.jwt;
  let signedInUserHouseNumber;

  // Handle removing appointments in the past
  let now = new Date();
  const nowDateString = appUtil.getYearMonthDayString(now);

  // handle days
  if (appointment.day.value < nowDateString) {
    return response.status(400).send({message: 'Appointment is in the past'});
    // handle same day + time
  } else if (appointment.day.value === nowDateString) {
    now.setTime(now.getTime() + (10 * 60000)); // now + 10 minutes
    const nowTimeString = appUtil.addPadding(now.getHours()) + ':'
      + appUtil.addPadding(now.getMinutes());
    if (appointment.time.value < nowTimeString) {
      if (appointment.time.value !== '01:00') { // the 01:00 is the next day actually
        return response.status(400).send({message: 'Appointment is in the past'});
      }
    }
  }

  admin.auth().verifyIdToken(jwtToken).then(decodedIdToken => {
    const signedInUserUid = decodedIdToken.uid;
    // console.log('signedInUserUid', signedInUserUid);

    return admin.auth().getUser(signedInUserUid);
  }).then(userRecord => {
    signedInUserHouseNumber = userRecord.displayName;
    // console.log('signedInUserHouseNumber', signedInUserHouseNumber);

    return admin.firestore().collection('days').get();
  }).then(querySnapshot => {
    const dayDocument = querySnapshot.docs.map(doc => {
      return {
        id: doc.id,
        isCurrentWeek: doc.data().isCurrentWeek,
      };
    }).find(doc => {
      return doc.id === appointment.day.value;
    });
    // console.log('dayDocument', dayDocument);
    const counterType = appUtil.setCorrectCounterType(dayDocument,
      appointment);
    // console.log('counterType', counterType);

    return admin.firestore().runTransaction(transaction => {
      return transaction.getAll(
        appUtil.userRef(signedInUserHouseNumber),
        appUtil.appointmentRef(appointment)
      )
        .then(docs => {
          const userDoc = docs[0];
          const appointmentDoc = docs[1];

          const userData = userDoc.data(); // counters: {dryers: 0, laundrymachine: 0, nextWeekDryer: 0, nextWeekLaundrymachine: 0}
          // console.log('userData', userData);
          const counter = userData.counters[counterType];
          // console.log('counter', counter);
          const isAbleToRemoveCounter = counter > 0;

          if (!isAbleToRemoveCounter) {
            response.status(207).send(
              {message: 'No appointments to remove!'});
            return Promise.resolve();
          }
          if (!appointmentDoc.exists) {
            response.status(207).send(
              {message: 'Appointment doesn\'t exist!'});
            return Promise.resolve();
          }
          if (appointmentDoc.data().houseNumber !== signedInUserHouseNumber) {
            response.status(207).send({
              message: 'You can\'t remove someone else\'s appointment'
            });
            return Promise.resolve();
          }

          transaction.update(appUtil.userRef(signedInUserHouseNumber),
            {[`counters.${counterType}`]: counter - 1});

          transaction.delete(admin.firestore().collection('appointments').doc(
            `${appointment.day.value}_${appointment.time.index}_${appointment.machineInfo.room.id}_${appointment.machineInfo.id}`)
          );

          response.status(200).send({message: 'Appointment removed!'});
          return Promise.resolve();
        });
    });
  }).catch(error => {
    console.log(error);
    response.status(400).send({message: 'error'});
  });
};
