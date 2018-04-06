const functions = require('firebase-functions');
const admin = require('firebase-admin');

const serviceAccount = require(
  "../fir-531f4-firebase-adminsdk-gcqo4-a8dd830b56");

const cors = require('cors')({
  origin: true
});

admin.initializeApp();

// admin.initializeApp({
//   credential: admin.credential.cert(serviceAccount),
//   databaseURL: 'https://fir-531f4.firebaseio.com',
// });

// apiKey: 'AIzaSyBN5qsGqVAzWwOgnYYToNFOYcLvCE0zXM0',
// authDomain: 'fir-531f4.firebaseapp.com',
// databaseURL: 'https://fir-531f4.firebaseio.com',
// projectId: 'fir-531f4',
// storageBucket: 'fir-531f4.appspot.com',
// messagingSenderId: '932886716822'

exports.registerUser = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {

    const user = request.body;

    console.log("user ", user);

    admin.database().ref('/users').child(user.houseNumber)
      .once("value").then(houseNumber => {
      houseNumber = houseNumber.val();
      console.log('houseNumber ', houseNumber);

      if (houseNumber === "") {
        admin.auth().createUser(user)
          .then(value => {
            console.log("Successfully created new user: ", value.uid);

            admin.database().ref('/users').child(user.houseNumber).set({
              email: user.email,
              admin: false,
              counter: 0,
              created: new Date().toJSON(),
              favouriteRoom: 'A',
              name: `${user.firstName} ${user.lastName}`
            });
            response.status(200).send();

          })
          .catch(error => {
            console.log("Error creating new user: ", error);
            response.status(400).send("User with this email already exists!");
          });

      } else {
        response.status(400).send("User with this housenumber already exists!");
      }
    });

  });

});

exports.demo = functions.https.onRequest((request, response) => {

  return cors(request, response, () => {
    console.log(request.body);
    setTimeout(() => {
      response.status(200).send('This is working just fine!');
    }, 100);
  });
});

exports.appointment = functions.https.onRequest((request, response) => {
  // Supported HTTP methods are: GET, POST, PUT, DELETE, and OPTIONS.
  // maybe also disable DELETE HTTP method,
  // THere is a CONNECT and HEAD method. What do they do if I make a request to them?
  // if (request.method === 'GET' || request.method === 'POST' || request.method === 'DELETE') {
  //   return response.status(403).send(`${request.method} is forbidden!`);
  // }
  return cors(request, response, () => {

    const appointmentRequest = request.body;
    console.log('request.body: ', appointmentRequest);
    console.log('request.method: ', request.method);

    admin.auth().verifyIdToken(appointmentRequest.idToken)
      .then((decodedToken) => {
        console.log('decodedToken: ' + decodedToken);
        // const signedInUserUid = decodedToken.uid;
        // console.log('signedInUserUid', signedInUserUid);
        //
        // admin.auth().getUser(signedInUserUid).then(userRecord => {
        //   const signedInUserHouseNumber = userRecord.displayName;
        //
        //   const isEmpty = appointmentRequest.houseNumber === '-';
        //
        //   let counterType;
        //   if (appointmentRequest.machineType === 'Laundrymachine') {
        //     counterType = 'laundryMachineCounter';
        //   } else {
        //     counterType = 'dryerCounter';
        //   }
        //
        //   if (isEmpty) {
        //     addAppointment(request, response, appointmentRequest,
        //       signedInUserHouseNumber, counterType);
        //   } else {
        //     removeAppointment(request, response, appointmentRequest,
        //       signedInUserHouseNumber, counterType);
        //   }
        // });
        response.status(200).send();
      }).catch((error) => {
        console.log('reason1 ', error);
        response.status(401).send();
      });
    });
});

function addAppointment(request, response, appointmentRequest,
                        signedInUserHouseNumber, counterType) {
  const counterDbRef = admin.database().ref(
    `users/${signedInUserHouseNumber}/${counterType}`);

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
  })
}

function removeAppointment(request, response, appointmentRequest,
                           signedInUserHouseNumber, counterType) {
  const counterDbRef = admin.database().ref(
    `users/${signedInUserHouseNumber}/${counterType}`);

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

// I have decided to not use the REST endpoints because I need an access token
// which is retrieved from googleapis.
// This accesstoken is available for 1 hour. And I have no idea how to refresh it.
// It is a lot of overhead to request an access token each time someone makes
// an appointment.

/// I WILL NOT NEED CORS if application is live. They are on the same origin!

// exports.removeOldDates = functions.https.onRequest((request, response) => {
//   // const rooms = ['A', 'B', 'C'];
//   // const machines = ['A1', 'A2', 'A3', 'A4', 'B1', 'B2', 'B3', 'B4', 'C1', 'C2', 'C3', 'C4'];
//   const rooms = ['D'];
//   const machines = ['D1'];
//
//
//   const yesterday = new Date();
//   yesterday.setDate(yesterday.getDate() - 1);
//
//   const nextWeek = new Date();
//   nextWeek.setDate(nextWeek.getDate() + 6);
//
//   const yesterdayString = getDayMonthYearString(yesterday);
//   const nextWeekString = getDayMonthYearString(nextWeek);
//
//   const roomsRef = admin.database().ref('/rooms');
//
//   const dataToAdd = ['-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'];
//
//   return cors(request, response, () => {
//     for (const room of rooms) {
//       for (const machine of machines) {
//         roomsRef.child(`${room}/machines/${machine}/appointments/${yesterdayString}`)
//           .remove().then(() => {
//           console.log('removed');
//         }).catch(error => {
//           console.log('error', error);
//           console.log('error while removing');
//         });
//
//         roomsRef.child(`${room}/machines/${machine}/appointments/${nextWeekString}`).set(dataToAdd)
//           .then(() => {
//             console.log('Synchronization succeeded');
//           })
//           .catch(error => {
//             console.log('error', error);
//             console.log('Synchronization failed');
//           });
//       }
//     }
//
//     response.status(200).send('OK');
//   });
// });

function getDayMonthYearString(date) {
  let day = date.getDate();
  day = day < 10 ? '0' + day : day;
  let month = date.getMonth() + 1;
  month = month < 10 ? '0' + month : month;
  const year = date.getFullYear();

  return `${day}-${month}-${year}`;
}
