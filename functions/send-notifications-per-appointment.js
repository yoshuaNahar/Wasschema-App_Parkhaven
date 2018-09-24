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

const topic = '/topics/appointmentIn30Minutes';

exports.handler = function (request, response) {
  const currentDate = new Date();
  currentDate.setDate(currentDate.getDate() + 85);
  console.log('currentDate', currentDate);

  // Check if I need to modify the minutes if it is less than 10
  let appointmentTimeIndex = 0;

  for (let i = 0; i < times.length; i++) {
    // the first dateTime where currentDate earlier than the appointment dateTime
    if (currentDate < currentDateWithTime(times[i])) {
      appointmentTimeIndex = i;
      break;
    }
  }
  console.log('appointmentTimeIndex', appointmentTimeIndex);

  const pushTokenPromise = admin.firestore().collection('pushTokens').get();
  const nextAppointmentsPromise = admin.firestore().collection('appointments')
    .where('time', '==', appointmentTimeIndex)
    .where('date', '==', getYearMonthDay(currentDate))
    .get();

  const pushTokensToSubscribeToNextTopic = [];

  Promise.all([pushTokenPromise, nextAppointmentsPromise])
    .then(values => {
      const pushTokenQuerySnapshot = values[0];
      const appointmentsQuerySnapshot = values[1];

      pushTokenQuerySnapshot.forEach(pushTokenQueryDoc => {
        const pushTokenHouseNumber = pushTokenQueryDoc.id;
        const pushToken = pushTokenQueryDoc.data().token;

        console.log('pushTokenHouseNumber + pushToken', pushTokenHouseNumber, pushToken);

        appointmentsQuerySnapshot.forEach(appQueryDoc => {
          const appData = appQueryDoc.data();
          console.log('appData', appData);
          // if true, then the user in the next appointment has a pushToken, so wants to receive notifications
          if (pushTokenHouseNumber === appData.houseNumber) {
            pushTokensToSubscribeToNextTopic.push(pushToken);
          }
        });
      });

      console.log('pushTokensToSubscribeToNextTopic', pushTokensToSubscribeToNextTopic);
      console.log('topic', topic);

      return admin.messaging()
        .subscribeToTopic(pushTokensToSubscribeToNextTopic, topic);
    })
    .then(result => {
      console.log(result);
      return admin.messaging().sendToTopic(topic,
        {data: {text: 'You have a laundry appointment in 30 minutes!'}})
    })
    .then(result => {
      console.log(result);
      return admin.messaging()
        .unsubscribeFromTopic(pushTokensToSubscribeToNextTopic, topic);
    })
    .then(result => {
      console.log(result);
      console.log('finihsed with subscribing, sending, unsubscribing');
      response.sendStatus(200);
    })
    .catch(error => {
      console.log(error);
      response.sendStatus(200);
    });
};

function currentDateWithTime(timeString) {
  const hourAndTimeStringArray = timeString.split(':');

  const date = new Date();
  date.setDate(date.getDate() + 85);

  date.setHours(hourAndTimeStringArray[0]);
  date.setMinutes(hourAndTimeStringArray[1]);
  console.log(date.toJSON()); // isoTime uses UTC timezone

  return date;
}

function getYearMonthDay(date) {
  const yearMonthDate = date.toISOString().split('T')[0];
  console.log('yearMonthDate', yearMonthDate);
  return yearMonthDate;
}
