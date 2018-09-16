/*
 * Steps:
 * 1. Verify authenticated userData with verifyIdToken
 * 2. Check if user with uid is inside the admin collection
 * 3. If he is delete, or else cancel request
 */

// request.body: {
//   jwt: 'jwt',
//   userToDelete: {
//     email: 'email',
//     houseNumber: 'houseNumber'
//   }
// }

const admin = require('firebase-admin');

exports.handler = function (request, response) {
  console.log('request.body', request.body);
  const requestBody = request.body;

  admin.auth().verifyIdToken(requestBody.jwt).then(decodedIdToken => {
    const signedInUserUid = decodedIdToken.uid;
    console.log('signedInUserUid', signedInUserUid);

    return admin.firestore().collection('admins').doc(signedInUserUid).get();
  }).then(adminDoc => {
    console.log('adminDoc.data()', adminDoc.data());
    if (adminDoc.data() === undefined) {
      throw Error();
    }
    return admin.auth().getUserByEmail(requestBody.userToDelete.email);
  }).then(userToDelete => {
    console.log('userToRemove', userToDelete);

    admin.firestore().collection('users')
      .doc(requestBody.userToDelete.id).set({});
    admin.firestore().collection('publicUsersInfo')
      .doc(requestBody.userToDelete.id).delete();

    return admin.auth().deleteUser(userToDelete.uid);
  }).then(() => {
    response.status(200).send({message: 'Account was removed successfully.'});
  }).catch(error => {
    response.status(400)
      .send({message: 'You are not authorized to delete a user.'});
  });
};
