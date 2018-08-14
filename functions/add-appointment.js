/*
 * Steps:
 * 1. Verify authenticated user with verifyIdToken
 * 2. Get authenticated user's houseNumber
 * 3. Check if the user's counter is valid (less than 3)
 * 4. Increment counter
 * 5. Check if spot is free when placing an appointment
 * 6. Add houseNumber on spot
 */

// request.body: {
//   appointment: {
//     room: 'A',
//     machineMetaData: {
//       name: 'A1',
//       type: 'Laundrymachine'
//     },
//     date: {
//       index: 0,
//       date: '2018-07-01'
//     },
//     time: {
//       index: 12,
//       value: 18:00
//     }
//   },
//   jwt: 'jwt'
// }
exports.handler = function (request, response, admin) {
  console.log('request.body', request.body);
  console.log('addAppointment');
  const appointment = request.body.appointment;
  const jwtToken = request.body.jwt;

  admin.auth().verifyIdToken(jwtToken).then(decodedIdToken => {
    const signedInUserUid = decodedIdToken.uid;
    console.log('signedInUserUid', signedInUserUid);

    return admin.auth().getUser(signedInUserUid); // What happens if this fails? I think the last catch is for the
  }).then(userRecord => {
    // User already authenticated, so increment counter
    const signedInUserHouseNumber = userRecord.displayName;
    const userCounterRef = admin.firestore().collection('users').doc(
      signedInUserHouseNumber);

    admin.firestore().runTransaction(transactionRef => {
      return transactionRef.get(userCounterRef).then(doc => {
        const userData = doc.data(); // counters: {dryers: 0, laundrymachine: 0}
        console.log('userData', userData);
        const counter = userData.counters[appointment.machineMetaData.type];

        console.log('counter', counter);

        const isAbleToWash = counter < 3;
        transactionRef.update(userCounterRef,
          {[`counters.${appointment.machineMetaData.type}`]: counter + 1});
        if (isAbleToWash) {
          return Promise.resolve('counter placed');
        } else {
          return Promise.reject('no more counters');
        }
      });
    }).then(result => {
      console.log('counterResult', result);
      // washCounter Incremented so add appointment
      const roomsRef = admin.firestore().collection('rooms').doc(
        appointment.room);
      admin.firestore().runTransaction(transactionRef => {
        return transactionRef.get(roomsRef).then(doc => {
          const houseNumberData = doc.data(); // {'A1': {'2018-08-01': {'0': '-', ...}, ...}, ...}

          const oldHouseNumber = houseNumberData[appointment.machineMetaData.name][appointment.day.value][appointment.time.index];
          console.log('oldHouseNumber', oldHouseNumber);
          console.log('signedInUserHouseNumber', signedInUserHouseNumber);
          transactionRef.update(roomsRef,
            {[`${appointment.machineMetaData.name}.${appointment.day.value}.${appointment.time.index}`]: signedInUserHouseNumber});

          if (oldHouseNumber === '-') {
            return Promise.resolve('Appointment spot available and placed');
          } else {
            return Promise.reject('Spot already taken, so reject promise');
          }
        });
      }).then(result => {
        console.log('Appointment placed!', result);
        return response.status(200).send();
      }).catch(err => {
        return response.status(207).send('Spot already taken!');
      });
    }).catch(error => {
      console.log('error', error);
      console.log('No appointments left!');
      response.status(207).send('No appointments left!');
    })
  });
};
