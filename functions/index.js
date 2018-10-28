const functions = require('firebase-functions');
const admin = require('firebase-admin');

const signUpFunction = require('./signup');
const addAppointmentFunction = require('./add-appointment');
const removeAppointmentFunction = require('./remove-appointment');
const deleteUserAsAdminFunction = require('./delete-user-as-admin');
const weeklyCleanUpFunction = require('./weekly-cleanup');
const dailyCleanUpFunction = require('./daily-cleanup');
const dailyHandleMaintenacesFunction = require('./daily-handle-maintenaces');
const sendNotificationsPerAppointmentFunction =
  require('./send-notifications-per-appointment');
const setNewNotificationBoardMessageFunction =
  require('./set-new-notification-board-message');

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

exports.signup = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {
    signUpFunction.handler(request, response);
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

exports.deleteUser = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {
    deleteUserAsAdminFunction.handler(request, response);
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

exports.setNewNotificationBoardMessage = functions.https.onRequest((request, response) => {
  return cors(request, response, () => {
    setNewNotificationBoardMessageFunction.handler(request, response);
  });
});

exports.sendNotificationsPerAppointment = functions.https
  .onRequest((request, response) => {
    return cors(request, response, () => {
      sendNotificationsPerAppointmentFunction.handler(request, response);
    });
  });
