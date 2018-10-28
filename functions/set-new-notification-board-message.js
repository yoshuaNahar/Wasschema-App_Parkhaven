/*
 * When a notification is accepted, add the new Notification field to true
 * inside the publicUsersInfo collection
 */

const admin = require('firebase-admin');

exports.handler = function (request, response) {
  const publicUsersInfo = admin.firestore().collection('publicUsersInfo');
  const batch = admin.firestore().batch();
  const pushTokens = [];
  const newNotificationTopic = '/topics/newNotification';

  const requestBody = request.body;
  admin.auth().verifyIdToken(requestBody.jwt).then(decodedIdToken => {
    const signedInUserUid = decodedIdToken.uid;
    // console.log('signedInUserUid', signedInUserUid);

    return admin.firestore().collection('admins').doc(signedInUserUid).get();
  }).then(adminDoc => {
    // console.log('adminDoc.data()', adminDoc.data());
    if (adminDoc.data() === undefined) {
      throw Error();
    }

    return publicUsersInfo.get();
  }).then(querySnapshot => {
    querySnapshot.forEach(publicUserInfoDoc => {
      batch.update(publicUsersInfo.doc(publicUserInfoDoc.id), {
        isNewNotificationAvailable: true
      });
    });

    return batch.commit();
  }).then(() => {
    return admin.firestore().collection('pushTokens').get();
  }).then(pushTokenQuerySnapshot => {
    pushTokenQuerySnapshot.forEach(pushTokenDoc => {
      pushTokens.push(pushTokenDoc.data().token);
    });

    return admin.messaging().subscribeToTopic(pushTokens,
      newNotificationTopic);
  }).then(result => {
    return admin.messaging().sendToTopic(newNotificationTopic, {
      notification: {
        title: 'Wasschema - Message alert!',
        body: 'A new message has been added to the notification board.'
      }
    });
  }).then(() => {
    response.status(200).send({message: 'Appointment removed!'});
  }).catch(err => {
    response.status(400).send({message: err.message});
  });
};
