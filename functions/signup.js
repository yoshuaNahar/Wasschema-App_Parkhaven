/*
 * Steps:
 * 1. Check that the houseNumber isn't already used.
 * 2. Add the user from the request to the backend.
 * 3. Send an email to the email address to accept.
 */

// request.body: {
//   firstName,
//   lastName,
//   email,
//   password,
//   houseNumber
// }

/*
{
  [fbUser.uid]: {
    admin: false,
    name: `${user.firstName} ${user.lastName}`
  },
  counters: {dryer: 0, laundrymachine: 0}
}
 */

const admin = require('firebase-admin');
const appUtil = require('./appointment-util');

exports.handler = function (request, response) {
  const user = request.body;
  console.log("user ", user);

  appUtil.userRef(user.houseNumber).get().then(userData => {
    const userDb = userData.data();
    console.log('userDb, If undefined create a null doc with that housenumber',
      userDb);
    const userExists = Object.keys(userDb).length !== 0; // object that is this: { }
    if (userExists) {
      response.status(400).send("User with this housenumber already exists!");
      return Promise.reject();
    }

    user.displayName = user.houseNumber; // I do this so that the displayName is also set when to user is created
    return admin.auth().createUser(user);
  }).then(fbUser => {
    console.log("Successfully created new user: ", fbUser.uid);

    appUtil.userRef(user.houseNumber).set({
      admin: false,
      counters: {
        dryer: 0,
        laundrymachine: 0,
        nextWeekDryer: 0,
        nextWeekLaundrymachine: 0
      }
    });

    admin.firestore().collection('/publicUsersData').doc(user.houseNumber)
      .set({favouriteRoom: 'A',});

    response.status(200).send();
  }).catch(error => {
    console.log("Error creating new user: ", error);
    response.status(400).send("User with this email already exists!");
  });
};
