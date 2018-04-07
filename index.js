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
