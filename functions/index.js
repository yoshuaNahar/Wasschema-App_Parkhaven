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

exports.handleMaintenaces = functions.https.onRequest(
  (request, response) => {
    return cors(request, response, () => {
      dailyHandleMaintenacesFunction.handler(request, response);
    });
  });
