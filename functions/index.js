const functions = require('firebase-functions');
const admin = require('firebase-admin');

const signUpFunction = require('./signup');
const addAppointmentFunction = require('./add-appointment');
const removeAppointmentFunction = require('./remove-appointment');
const weeklyCleanUpFunction = require('./weekly-cleanup');
const dailyCleanUpFunction = require('./daily-cleanup');
const manageMaintenancesFunction = require('./maintenance');

const cors = require('cors')({
  origin: true
});

const serviceAccount = require(
  "./fir-531f4-firebase-adminsdk-gcqo4-a8dd830b56");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: 'https://fir-531f4.firebaseio.com'
});

admin.firestore().settings({timestampsInSnapshots: true});

// TODO: use this to add the other data
exports.helloWorld = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {
    const doc = admin.firestore().collection('roomsInfo').doc('A');
    doc.update({'A1.2018-08-08': {0: '-', 1: '-', 2: '-', 3: '-', 4: '-', 5: '-', 6: '-', 7: '-', 8: '-', 9: '-', 10: '-', 11: '-', 12: '-'}});
    doc.update({'A1.2018-08-09': {0: '-', 1: '-', 2: '-', 3: '-', 4: '-', 5: '-', 6: '-', 7: '-', 8: '-', 9: '-', 10: '-', 11: '-', 12: '-'}});
    doc.update({'A1.2018-08-10': {0: '-', 1: '-', 2: '-', 3: '-', 4: '-', 5: '-', 6: '-', 7: '-', 8: '-', 9: '-', 10: '-', 11: '-', 12: '-'}});
    doc.update({'A1.2018-08-11': {0: '-', 1: '-', 2: '-', 3: '-', 4: '-', 5: '-', 6: '-', 7: '-', 8: '-', 9: '-', 10: '-', 11: '-', 12: '-'}});
    doc.update({'A1.2018-08-12': {0: '-', 1: '-', 2: '-', 3: '-', 4: '-', 5: '-', 6: '-', 7: '-', 8: '-', 9: '-', 10: '-', 11: '-', 12: '-'}});
    doc.update({'A1.2018-08-13': {0: '-', 1: '-', 2: '-', 3: '-', 4: '-', 5: '-', 6: '-', 7: '-', 8: '-', 9: '-', 10: '-', 11: '-', 12: '-'}});
    doc.update({'A1.2018-08-14': {0: '-', 1: '-', 2: '-', 3: '-', 4: '-', 5: '-', 6: '-', 7: '-', 8: '-', 9: '-', 10: '-', 11: '-', 12: '-'}});

    response.status(200).send('Hello World!');
  });
});

exports.addAppointment = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {
    addAppointmentFunction.handler(request, response);
  });
});

exports.removeAppointment = functions.https.onRequest((request, response) => {
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

exports.maintenances = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {
    manageMaintenancesFunction.handler(request, response);
  });
});
