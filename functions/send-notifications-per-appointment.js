/*
 * Check which people have an appointment in the next 30 minutes.
 * Get all the people that also have notifications tokens on the server
 * Add them to a topic
 * Send a message to all the people in that topic
 * Unsubscribe everyone from that topic
 */

const admin = require('firebase-admin');
const times = ['05:30', '07:00', '08:30', '10:00', '11:30', '13:00', '14:30',
  '16:00', '17:30', '19:00', '20:30', '22:00', '23:30'];
// There is no notification for the last dryer at 01:00

const topic = '/topics/appointmentIn30Minutes';

exports.handler = function (request, response) {
  const currentDate = new Date();

  // Check if I need to modify the minutes if it is less than 10
  let appointmentTimeIndex = 0;

  for (let i = 0; i < times.length; i++) {
    // the first dateTime where currentDate earlier than the appointment dateTime
    if (currentDate < currentDateWithTime(times[i])) {
      appointmentTimeIndex = i;
      break;
    }
  }
  // console.log('appointmentTimeIndex', appointmentTimeIndex);

  const pushTokenPromise = admin.firestore().collection('pushTokens').get();
  const nextLaundryAppointmentsPromise = admin.firestore().collection(
    'appointments')
    .where('time', '==', appointmentTimeIndex)
    .where('date', '==', getYearMonthDay(currentDate))
    .get();
  const nextDryerAppointmentsPromise = admin.firestore().collection(
    'appointments')
    .where('time', '==', appointmentTimeIndex - 1)
    .where('date', '==', getYearMonthDay(currentDate))
    .get();

  const pushTokensToSubscribeToNextTopic = [];

  Promise.all([pushTokenPromise,
    nextLaundryAppointmentsPromise,
    nextDryerAppointmentsPromise])
    .then(values => {
      const pushTokenQuerySnapshot = values[0];
      const laundryAppointmentsQuerySnapshot = values[1];
      const dryerAppointmentsQuerySnapshot = values[2];

      pushTokenQuerySnapshot.forEach(pushTokenQueryDoc => {
        const pushTokenHouseNumber = pushTokenQueryDoc.id;
        const pushToken = pushTokenQueryDoc.data().token;

        // console.log('pushTokenHouseNumber + pushToken', pushTokenHouseNumber, pushToken);

        laundryAppointmentsQuerySnapshot.docs
          .filter(doc => doc.get('machine').includes('1') ||
            doc.get('machine').includes('2'))
          .forEach(appQueryDoc => {
            const appData = appQueryDoc.data();
            // console.log('appData', appData);
            // if true, then the user in the next appointment has a pushToken, so wants to receive notifications
            if (pushTokenHouseNumber === appData.houseNumber) {
              pushTokensToSubscribeToNextTopic.push(pushToken);
            }
          });

        dryerAppointmentsQuerySnapshot.docs
          .filter(doc => doc.get('machine').includes('3') ||
            doc.get('machine').includes('4'))
          .forEach(appQueryDoc => {
            const appData = appQueryDoc.data();
            // console.log('appData', appData);
            // if true, then the user in the next appointment has a pushToken, so wants to receive notifications
            if (pushTokenHouseNumber === appData.houseNumber) {
              pushTokensToSubscribeToNextTopic.push(pushToken);
            }
          });
      });

      // console.log('pushTokensToSubscribeToNextTopic', pushTokensToSubscribeToNextTopic);
      // console.log('topic', topic);

      return admin.messaging()
        .subscribeToTopic(pushTokensToSubscribeToNextTopic, topic);
    })
    .then(() => {
      return admin.messaging().sendToTopic(topic, {
        notification: {
          title: 'Wasschema - Appointment alert!',
          body: 'You have a laundry appointment in 30 minutes!'
        }
      });
    })
    .then(() => {
      return admin.messaging()
        .unsubscribeFromTopic(pushTokensToSubscribeToNextTopic, topic);
    })
    .then(() => {
      response.sendStatus(200);
    })
    .catch(error => {
      if (error.code === 'messaging/invalid-argument') {
        response.status(200).send({message: 'No appointments'});
      } else {
        response.status(400).send({message: error.message});
      }
    });
};

function currentDateWithTime(timeString) {
  const hourAndTimeStringArray = timeString.split(':');

  const date = new Date();

  date.setUTCHours(hourAndTimeStringArray[0] - 2); // Because the times are in CET
  date.setUTCMinutes(hourAndTimeStringArray[1]);
  // console.log('date.toISOString()', date.toISOString());

  return date;
}

function getYearMonthDay(date) {
  return date.toISOString().split('T')[0];
}
