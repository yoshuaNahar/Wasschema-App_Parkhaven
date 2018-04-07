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

        admin.auth().getUser(signedInUserUid)
          .then(userRecord => {
            const signedInUserHouseNumber = userRecord.displayName;

            const isEmpty = appointment.houseNumber === '-';

            let counterType;
            if (appointment.machineType === 'Laundrymachine') {
              counterType = 'laundryMachineCounter';
            } else {
              counterType = 'dryerCounter';
            }

            if (isEmpty) {
              addAppointment(request, response, appointment, signedInUserHouseNumber, counterType);
            } else {
              removeAppointment(request, response, appointment, signedInUserHouseNumber, counterType);
            }
          });
      }).catch(error => {
      console.log('error', error);
      response.status(401).send();
    });
  });
});

function addAppointment(request, response, appointmentRequest, signedInUserHouseNumber, counterType) {
  const counterDbRef = admin.database().ref(`users/${signedInUserHouseNumber}/${counterType}`);

  const houseNumberDbRef = admin.database().ref(
    `rooms/${appointmentRequest.room}/machines/${appointmentRequest.machine}/appointments/${appointmentRequest.date}/${appointmentRequest.time}`
  );

  counterDbRef.once('value').then(counter => {
    counter = counter.val();
    console.log('counter', counter);
    if (counter < 3) {
      houseNumberDbRef.once('value').then(houseNumber => {
        houseNumber = houseNumber.val();
        console.log('houseNumber', houseNumber);
        if (houseNumber === '-') {
          counterDbRef.set(++counter).then(() => {
              houseNumberDbRef.set(signedInUserHouseNumber).then(() => {
                console.log('here');
                response.status(200).send();
              });
            }
          );
        } else {
          response.status(400).send('Spot already taken');
        }
      });
    } else {
      response.status(400).send('No appointments left');
    }
  });
}

function removeAppointment(request, response, appointmentRequest, signedInUserHouseNumber, counterType) {
  const counterDbRef = admin.database().ref(`users/${signedInUserHouseNumber}/${counterType}`);

  const houseNumberDbRef = admin.database().ref(
    `rooms/${appointmentRequest.room}/machines/${appointmentRequest.machine}/appointments/${appointmentRequest.date}/${appointmentRequest.time}`
  );

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
