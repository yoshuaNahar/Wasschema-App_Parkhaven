const functions = require('firebase-functions');
const admin = require('firebase-admin');

const serviceAccount = require(
  "../fir-531f4-firebase-adminsdk-gcqo4-a8dd830b56");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: 'https://fir-531f4.firebaseio.com'
});

const cors = require('cors')({
  origin: true
});

async function test(response, userCounterDbRef, userHouseNumberDbRef) {
  const counterResult = await userCounterDbRef.transaction(washCounter => {
    console.log('washCounter', washCounter); // debug
    const washLimitReached = washCounter < 3;
    if (washLimitReached) {
      return washCounter + 1;
    }
    // else returns undefined, which will cause the transaction to abort
  });
  if (counterResult.committed) {
    console.log(userHouseNumberDbRef.toString());
    const userHouseNumberResult = await userHouseNumberDbRef.transaction(
      houseNumber => {
        console.log('houseNumber', houseNumber);
        if (houseNumber == null) {
          return -1; // because transactions return null on first attempt
        }
        if (houseNumber === '-') {
          return '401';
        }
        // else returns undefined, which will cause the transaction to abort
      });
    if (userHouseNumberResult.committed) {
      console.log('Appointment placed!');
      return response.status(200);
    } else { // rollback counter and return houseNumberJustTaken message
      userCounterDbRef.set(counterResult.snapshot.val());
      return response.status(207).body('Spot already taken!');
    }
  } else {
    console.log('No appointments left!');
    return response.status(207).body('No appointments left!');
  }
}

exports.appointmentTest = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {
    console.log('request.body', request.body);

    const userCounterDbRef = counterDbRef('401', 'laundryMachineCounter');
    const userHouseNumberDbRef = houseNumberDbRef({
      room: 'A',
      machine: 'A1',
      date: '25-04-2018',
      time: 0
    });
    test(response, userCounterDbRef, userHouseNumberDbRef).fin((res) => res.send());
  });
});

/*
 * Steps:
 * 1. Verify authenticated user with verifyIdToken
 * 2. Get houseNumber
 * 3. Check if this is request to add an appointment or remove one
 * 4. Check if this is a laundry or dryer appointment
 * 5. Check if the user's counter is valid
 * 6. Check if spot is free when placing an appointment or if taken spot is really his, when removing an appointment
 */
exports.appointment = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {

    const appointment = request.body;
    console.log('request.body: ', appointment);

    admin.auth().verifyIdToken(appointment.idToken)
    .then(decodedIdToken => {
      const signedInUserUid = decodedIdToken.uid;
      console.log('signedInUserUid', signedInUserUid);

      return admin.auth().getUser(signedInUserUid); // What happens if this fails? I think the last catch is for the
    }).then(userRecord => {
      const signedInUserHouseNumber = userRecord.displayName;
      const isEmpty = appointment.houseNumber === '-';

      let counterType;
      if (appointment.machineType === 'Laundrymachine') {
        counterType = 'laundryMachineCounter';
      } else {
        counterType = 'dryerCounter';
      }

      // if (isEmpty) {
      return addAppointment(request, response, appointment,
        signedInUserHouseNumber, counterType);
      // } else {
      //   return removeAppointment(request, response, appointment,
      //     signedInUserHouseNumber, counterType);
      // }
    }).catch(error => {
      console.log('error', error);
      response.status(401).send();
    });
  });
});

function addAppointment(request, response, appointmentRequest,
  signedInUserHouseNumber, counterType) {

  const userCounterDbRef = counterDbRef(signedInUserHouseNumber, counterType);
  const userHouseNumberDbRef = houseNumberDbRef(appointmentRequest);

  userCounterDbRef.transaction(washCounter => {
    console.log('washCounter', washCounter); // debug
    const washLimitReached = washCounter < 3;
    if (washLimitReached) {
      return washCounter + 1;
    }
    // else returns undefined, which will cause the transaction to abort
  }).then(counterTransaction => {
    if (counterTransaction.committed) {
      return userHouseNumberDbRef.transaction(houseNumber => {
        console.log('houseNumber', houseNumber);
        if (houseNumber === '-') {
          return signedInUserHouseNumber;
        }
        // else returns undefined, which will cause the transaction to abort
      });
    } else {
      console.log('No appointments left!');
      return Promise.reject('No appointments left!');
      // return response.status(207).send('No appointments left!');
    }
  }).then(houseNumberTransaction => {
    if (houseNumberTransaction.committed) {
      console.log('Appointment placed!');
      return response.status(200).send();
    }
    // rollback counter and return houseNumberJustTaken
  }, e => {
    return response.status(209).send(e);
  });
}

function removeAppointment(request, response, appointmentRequest,
  signedInUserHouseNumber, counterType) {

  const counterDbRef = counterDbRef(signedInUserHouseNumber, counterType);
  const houseNumberDbRef = houseNumberDbRef(appointmentRequest);

  counterDbRef.once('value').then(counter => {
    counter = counter.val();
    console.log('counter', counter);
    if (counter > 0) {
      houseNumberDbRef.once('value').then(houseNumber => {
        houseNumber = houseNumber.val();
        console.log('houseNumber', houseNumber);
        if (houseNumber !== '-' ||
          houseNumber === signedInUserHouseNumber) {
          counterDbRef.set(--counter).then(() => {
            houseNumberDbRef.set('-').then(() => {
              response.status(200).send();
            });
          });
        } else {
          response.status(400).send('Not your appointment!');
        }
      });
    } else {
      response.status(400).send('You have no appointments!');
    }
  });
}

function houseNumberDbRef(appointmentRequest) {
  return admin.database().ref(
    `rooms/${appointmentRequest.room}/machines/${appointmentRequest.machine}/appointments/${appointmentRequest.date}/${appointmentRequest.time}`
  );
}

function counterDbRef(houseNumber, counterType) {
  return admin.database().ref(
    `users/${houseNumber}/${counterType}`);
}
