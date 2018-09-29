/*
 * jwt: jwt
 */
const admin = require('firebase-admin');

exports.handler = function (request, response) {
  const jwtToken = request.body.jwt;

  admin.auth().verifyIdToken(jwtToken).then(decodedIdToken => {
    return admin.auth().getUser(decodedIdToken.uid);
  }).then(userRecord => {
    const signedInUserHouseNumber = userRecord.displayName;
    console.log('signedInUserHouseNumber', signedInUserHouseNumber);

    return admin.firestore().collection('pushTokens')
      .doc(signedInUserHouseNumber).get();
  }).then(tokenDoc => {
    const pushToken = tokenDoc.data().token;

    return admin.messaging().sendToDevice(pushToken, {
      notification: {
        title: 'Wasschema - Demo notification!',
        body: 'This is an example of a notification that you will receive.'
      }
    });
  }).then(() => {
    response.status(200).send({message: 'Notification Received'});
  })

};

