// Daily
// days collection: - Update isDisplayable

const admin = require('firebase-admin');

exports.handler = function (request, response) {
  let docToSetIsDisplayableToFalse;
  let docToSetIsDisplayableToTrue;

  admin.firestore().collection('days').get().then(querySnapshot => {
    let previousDoc = null;
    let currentDoc = null;
    querySnapshot.forEach(doc => {
      console.log('doc.data()', doc.data());
      previousDoc = currentDoc;
      currentDoc = doc.data();

      if (previousDoc === null) {
        return;
      }

      if (!previousDoc.isDisplayable && currentDoc.isDisplayable) {
        docToSetIsDisplayableToFalse = {id: doc.id, doc: currentDoc};
      } else if (previousDoc.isDisplayable && !currentDoc.isDisplayable) {
        docToSetIsDisplayableToTrue = {id: doc.id, doc: currentDoc};
      }
    });

    const batch = admin.firestore().batch();
    const dateIsDisplayableToFalseRef = admin.firestore().collection('days')
      .doc(docToSetIsDisplayableToFalse.id);
    const dateIsDisplayableToTrueRef = admin.firestore().collection('days')
      .doc(docToSetIsDisplayableToTrue.id);
    batch.update(dateIsDisplayableToFalseRef, {'isDisplayable': false});
    batch.update(dateIsDisplayableToTrueRef, {'isDisplayable': true});

    return batch.commit();
  }).then(() => {
    response.status(200).send('Remove old dates logic comes here!');
  });
};
