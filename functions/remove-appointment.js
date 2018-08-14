/*
 * Steps:
 * 1. Verify authenticated user with verifyIdToken
 * 2. Get authenticated user's houseNumber
 * 3. Check if the spot to remove has the same houseNumber
 * 4. Remove the houseNumber from that spot
 * 5. Decrement counter
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
  const appointment = request.body.appointment;
  const jwtToken = request.body.jwt;

  admin.auth().verifyIdToken(jwtToken).then(decodedIdToken => {
    const signedInUserUid = decodedIdToken.uid;
    console.log('signedInUserUid', signedInUserUid);

    return admin.auth().getUser(signedInUserUid); // What happens if this fails? I think the last catch is for the
  }).then(userRecord => {
    const signedInUserHouseNumber = userRecord.displayName;
    console.log('signedInUserHouseNumber', signedInUserHouseNumber);

    const roomsRef = admin.firestore().collection('rooms').doc(
      appointment.room);

    admin.firestore().runTransaction(transactionRef => {
      return transactionRef.get(roomsRef).then(doc => {
        const houseNumberData = doc.data(); // {'A1': {'2018-08-01': {'0': '-', ...}, ...}, ...}

        const currentlyPlacedHouseNumber = houseNumberData[appointment.machineMetaData.name][appointment.day.value][appointment.time.index];
        transactionRef.update(roomsRef,
          {[`${appointment.machineMetaData.name}.${appointment.day.value}.${appointment.time.index}`]: '-'});

        if (currentlyPlacedHouseNumber === signedInUserHouseNumber) {
          return Promise.resolve(
            'Appointment spot was of the current user and is removed');
        } else {
          return Promise.reject('Spot not from the current user');
        }
      });
    }).then(result => {
      // appointment reset succesfully
      const userCounterRef = admin.firestore().collection('users').doc(
        signedInUserHouseNumber);

      admin.firestore().runTransaction(transactionRef => {
        return transactionRef.get(userCounterRef).then(doc => {
          const userData = doc.data(); // counters: {dryers: 0, laundrymachine: 0}
          console.log('userData', userData);
          const counter = userData.counters[appointment.machineMetaData.type];

          console.log('counter', counter);

          const isRemovable = counter > 0;
          transactionRef.update(userCounterRef,
            {[`counters.${appointment.machineMetaData.type}`]: counter - 1});
          if (isRemovable) {
            return Promise.resolve('counter removed');
          } else {
            return Promise.reject('counter lower than 0?');
          }
          console.log('Appointment removed!');
          return response.status(200).send();
        });
      }).catch(err => {
        return response.status(207).send('Not your appointment to remove!');
      });
    });
  });
};
