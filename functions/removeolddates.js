exports.handler = function (request, response, admin) {
  return response.status(200).send('Remove old dates logic comes here!');
};

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
