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

exports.handler = function (request, response, admin) {
  const user = request.body;

  console.log("user ", user);

  admin.firestore().collection('/users').doc(
    user.houseNumber).get().then(userData => {
    const userDb = userData.data();
    console.log('userDb, If undefined create a null doc with that housenumber',
      userDb);
    const userExists = Object.keys(userDb).length !== 0; // object that is this: { }
    if (userExists) {
      response.status(400).send("User with this housenumber already exists!");
    } else {
      user.displayName = user.houseNumber; // I do this so that the displayName is also set when to user is created
      admin.auth().createUser(user)
      .then(fbUser => {
        console.log("Successfully created new user: ", fbUser.uid);

        admin.firestore().collection('/users').doc(user.houseNumber).set(
          {
            admin: false,
            name: `${user.firstName} ${user.lastName}`,
            counters: {dryer: 0, laundrymachine: 0}
          }
        );

        admin.firestore().collection('/publicUsersData').doc(
          user.houseNumber).set({
          favouriteRoom: 'A',
        });

        response.status(200).send();
      })
      .catch(error => {
        console.log("Error creating new user: ", error);
        response.status(400).send("User with this email already exists!");
      });
    }
  });
};
