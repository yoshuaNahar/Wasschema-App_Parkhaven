const functions = require('firebase-functions');
const admin = require('firebase-admin');

const signUpFunction = require('./signup');
const addAppointmentFunction = require('./add-appointment');
const removeAppointmentFunction = require('./remove-appointment');
const weeklyCleanUpFunction = require('./weekly-cleanup');
const dailyCleanUpFunction = require('./daily-cleanup');
const dailyHandleMaintenacesFunction = require('./daily-handle-maintenaces');

const cors = require('cors')({
  origin: true
});

const serviceAccount =
  require("./fir-531f4-firebase-adminsdk-gcqo4-a8dd830b56");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: 'https://fir-531f4.firebaseio.com'
});

admin.firestore().settings({timestampsInSnapshots: true});

exports.helloWorld = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {
    response.status(200).send('Hello World!');
    admin.firestore().collection('days').get().then(querySnapshot => {
      querySnapshot.forEach(doc => {
        const day = {
          id: doc.id,
          isCurrentWeek: doc.data().isCurrentWeek,
          isDisplayable: doc.data().isDisplayable,
        };

        console.log(day);
      });
    });
  });
});

exports.addAppointment = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {
    addAppointmentFunction.handler(request, response);
  });
});

exports.removeAppointment = functions.https.onRequest(
  (request, response) => {
    return cors(request, response, () => {
      removeAppointmentFunction.handler(request, response);
    });
  });

exports.signup = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {
    signUpFunction.handler(request, response);
  });
});

exports.weeklyCleanUp = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {
    weeklyCleanUpFunction.handler(request, response);
  });
});

exports.dailyCleanUp = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {
    dailyCleanUpFunction.handler(request, response);
  });
});

exports.handleMaintenaces = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {
    dailyHandleMaintenacesFunction.handler(request, response);
  });
});

const http = require('http');
// const https = require('https'); on the server
exports.demo2 = functions.https.onRequest((request, response) => {
  admin.firestore().collection('pushTokens').doc(
    'eGeRSeqkKgE:APA91bHSlej04rH8JJL_AeWETOn7KVmeFo6NG9pkPWr__jpIldDbwRr_BeUjA0863TxNiXIyCG56vbPRH0ZF1wMk5_44j7gTbhPEtoLqDMsaE3BmyRT-YCCmhFME9M_Tv67NM-b4mC8I')
    .get().then(snap => {
    const newToken = snap.id;
    console.log('newToken', newToken);

    admin.messaging().subscribeToTopic(newToken,
      '/topics/appointmentIn30Minutes')
      .then(mtmr => {
        console.log('mtmr.errors', mtmr.errors);
        console.log('mtmr', mtmr);
      });

    response.status(200).send('OK');
  });
});

exports.addClientTokenToTopic = functions.firestore.document(
  'pushTokens/{tokenId}')
  .onCreate((snapshot, context) => {
    console.log('I was here');

    const newToken = snapshot.id;

    admin.messaging().subscribeToTopic(newToken,
      '/topics/appointmentIn30Minutes')
      .then(mtmr => {
        console.log(mtmr.errors);
      });
  });

// post applicationToken to topic
// https://iid.googleapis.com/iid/v1/nKctODamlM4:CKrh_PC8kIb7O...clJONHoA/rel/topics/movies
//   Content - Type:application / json
//   Authorization:key = AIzaSyZ-1u...0GBYzPu7Udno5aA

// Get authorizationToken
// const scopes = 'https://www.googleapis.com/auth/firebase.messaging';
// function getAccessToken() {
//   return new Promise(function(resolve, reject) {
//     var key = require('./fir-531f4-firebase-adminsdk-gcqo4-a8dd830b56.json');
//     var jwtClient = new google.auth.JWT(
//       key.client_email,
//       null,
//       key.private_key,
//       scopes,
//       null
//     );
//     jwtClient.authorize(function(err, tokens) {
//       if (err) {
//         reject(err);
//         return;
//       }
//       resolve(tokens.access_token);
//     });
//   });
// }

