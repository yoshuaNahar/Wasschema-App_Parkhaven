exports.handler = function (request, response, admin) {
  const user = request.body;

  console.log("user ", user);

  admin.database().ref('/users').child(user.houseNumber)
  .once("value").then(houseNumber => {
    houseNumber = houseNumber.val();
    console.log('houseNumber ', houseNumber);

    if (houseNumber !== "") {
      response.status(400).send("User with this housenumber already exists!");
    } else {
      admin.auth().createUser(user)
      .then(fbUser => {
        console.log("Successfully created new user: ", fbUser.uid);

        admin.database().ref('/users').child(user.houseNumber).set(
          {
            [fbUser.uid]: {
              email: user.email,
              admin: false,
              created: new Date().toJSON(),
              favouriteRoom: 'A',
              name: `${user.firstName} ${user.lastName}`
            },
            counters: { Dryer: 0, Laundrymachine: 0 }
          }
        );

        response.status(200).send();
      })
      .catch(error => {
        console.log("Error creating new user: ", error);
        response.status(400).send("User with this email already exists!");
      });
    }
  });
};

