const functions = require('firebase-functions');
const admin = require('firebase-admin');

const signUpFunction = require('./signup');
const addAppointmentFunction = require('./add-appointment');
const removeAppointmentFunction = require('./remove-appointment');
const removeOldDatesFunction = require('./removeolddates');

const cors = require('cors')({
  origin: true
});

const serviceAccount = require("./fir-531f4-firebase-adminsdk-gcqo4-a8dd830b56");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: 'https://fir-531f4.firebaseio.com'
});

// TODO: use this to add the other data
exports.helloWorld = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {
    const doc = admin.firestore().collection('rooms').doc('A');
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
    addAppointmentFunction.handler(request, response, admin);
  });
});

exports.removeAppointment = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {
    removeAppointmentFunction.handler(request, response, admin);
  });
});

exports.signup = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {
    signUpFunction.handler(request, response, admin);
  });
});

exports.removeOldDates = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {
    removeOldDatesFunction.handler(request, response, admin);
  });
});
