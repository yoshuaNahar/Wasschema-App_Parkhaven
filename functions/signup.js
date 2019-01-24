/*
 * Steps:
 * 1. Check that the houseNumber isn't already used.
 * 2. Create Auth user (Firebase checks if email already exists)
 * 3. Add the userData from the request to the backend.
 */

// request.body: {
//   email,
//   password,
//   houseNumber
// }

/*
  /users/{houseNumber}: {
    admin: false,
    email: email,
    counters: {dryer: 0, laundrymachine: 0}
  }
*/

const admin = require('firebase-admin');
const appUtil = require('./appointment-util');

exports.handler = function (request, response) {
  const user = request.body;

  appUtil.userRef(user.houseNumber).get().then(userData => {
    const userDb = userData.data();

    if (userDb === undefined) { // houseNumber doesn't exist
      throw new Error('The house number is invalid.');
    }

    const accountExists = Object.keys(userDb).length > 0; // object that is not this: { }
    if (accountExists) {
      throw new Error('The house number is already in use by another account.');
    }

    // I do this so that the displayName is also set when to auth user is created
    user.displayName = user.houseNumber;
    return admin.auth().createUser(user);
  }).then(() => {

    appUtil.userRef(user.houseNumber).set({
      admin: false,
      email: user.email,
      counters: {
        dryer: 0,
        laundrymachine: 0,
        nextWeekDryer: 0,
        nextWeekLaundrymachine: 0
      }
    });

    admin.firestore().collection('/publicUsersInfo').doc(user.houseNumber)
      .set({favouriteRoom: 'A', isNewNotificationAvailable: false});

    response.status(200).send();
  }).catch(error => {
    response.status(400).send(error.message);
  });
};
