/*
 * Steps:
 * 1. Verify authenticated user with verifyIdToken
 * 2. Get authenticated user's houseNumber
 * 3. Check if appointment is for nextWeek or this week
 * 4. Check if the user's counter is valid (less than 3)
 * 5. Check if spot is free when placing an appointment
 * 6. Increment counter
 * 7. Add appointment
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
  console.log('addAppointment');
  console.log('request.body', request.body);
  const appointment = request.body.appointment;
  const jwtToken = request.body.jwt;
  let signedInUserHouseNumber;

  admin.auth().verifyIdToken(jwtToken).then(decodedIdToken => {
    const signedInUserUid = decodedIdToken.uid;
    console.log('signedInUserUid', signedInUserUid);

    return admin.auth().getUser(signedInUserUid); // What happens if this fails? I think the last catch is for the
  }).then(userRecord => {
    // User already authenticated, so increment counter
    signedInUserHouseNumber = userRecord.displayName;
    console.log('signedInUserHouseNumber', signedInUserHouseNumber);

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
    console.log('dayDocument', dayDocument);
    const counterType = appUtil.setCorrectCounterType(dayDocument, appointment);
    console.log('counterType', counterType);

    return admin.firestore().runTransaction(transaction => {
      return transaction.getAll(appUtil.userRef(signedInUserHouseNumber),
        appUtil.appointmentRef(appointment))
        .then(docs => {
          const userDoc = docs[0];
          const appointmentDoc = docs[1];

          const userData = userDoc.data(); // counters: {dryers: 0, laundrymachine: 0, nextWeekDryer: 0, nextWeekLaundrymachine: 0}
          console.log('userData', userData);
          const counter = userData.counters[counterType];
          console.log('counter', counter);
          const isAbleToWash = counter < 3;

          if (!isAbleToWash) {
            response.status(207).send({message: 'No appointments left!'});
            return Promise.resolve();
          }

          if (appointmentDoc.exists) {
            console.log('Spot already taken, so reject promise');
            response.status(207).send({message: 'Spot already taken!'});
            return Promise.resolve();
          }

          transaction.update(appUtil.userRef(signedInUserHouseNumber),
            {[`counters.${counterType}`]: counter + 1});

          admin.firestore().collection('appointments')
            .doc(
              `${appointment.day.value}_${appointment.time.index}_${appointment.machineInfo.room.id}_${appointment.machineInfo.id}`)
            .set({
              houseNumber: signedInUserHouseNumber,
              room: appointment.machineInfo.room.id,
              machine: appointment.machineInfo.id,
              date: appointment.day.value,
              time: appointment.time.index
            });

          console.log('Appointment spot available and placed');
          response.status(200).send({message: 'Appointment placed!'});
          return Promise.resolve();
        });
    });
  }).catch(error => {
    console.log(error);
  });
};
