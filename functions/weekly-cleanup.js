// Weekly
// days collection: - Update isCurrentWeek
//                  - Remove previousWeek
//                  - Create new nextWeek
// users collection: - Of each userData, set nextWeekCounters to 0 and move the values of nextWeekCounters to the currentWeek

const admin = require('firebase-admin');

// TODO: this is not tested. test it tomorrow
exports.handler = function (request, response) {
  let daysToRemove = [];
  let daysChangeIsCurrentWeek = [];
  let lastDay;

  admin.firestore().collection('days').get().then(querySnapshot => {
    querySnapshot.docs.forEach(doc => {
      const day = {id: doc.id, doc: doc.data()};

      if (day.doc.isCurrentWeek) {
        daysToRemove.push(day);
      } else {
        daysChangeIsCurrentWeek.push(day);
      }

      lastDay = day;
    });

    const batch = admin.firestore().batch();
    const daysCollection = admin.firestore().collection('days');

    daysToRemove.forEach(day => {
      batch.delete(daysCollection.doc(day.id));
    });

    daysChangeIsCurrentWeek.forEach(day => {
      batch.update(daysCollection.doc(day.id), {
        isCurrentWeek: true,
        isDisplayable: true // You don't actually have to do this,
        // since on the sunday night all the days of next week will already have this on isDisplayable: true
      });
    });

    // create 7 days after the last date in the days collection
    const lastDayDate = new Date(lastDay.id);
    console.log(lastDayDate);
    const daysToCreate = [];
    for (let i = 0; i < 7; i++) {
      lastDayDate.setDate(lastDayDate.getDate() + 1);
      console.log('forLoop', lastDayDate);
      daysToCreate.push(getYearMonthDayString(lastDayDate));
    }

    daysToCreate.forEach(dayString => {
      batch.set(daysCollection.doc(dayString), {
        isDisplayable: false,
        isCurrentWeek: false
      });
    });

    return batch.commit();
  }).then(() => {
    // Here comes the code to update the users counters
    return admin.firestore().collection('users').get();
  }).then(querySnapshot => {
    const batch = admin.firestore().batch();
    const users = [];

    querySnapshot.forEach(userDoc => {
      users.push({id: userDoc.id, data: userDoc.data()});
    });

    users.forEach(user => {
      batch.update(admin.firestore().collection('users').doc(user.id), {
        counters: {
          dryer: user.data.counters.nextWeekDryer,
          laundrymachine: user.data.counters.nextWeekLaundrymachine,
          nextWeekDryer: 0,
          nextWeekLaundrymachine: 0
        }
      });
    });

    return batch.commit();
  }).then(() => {
    response.status(200).send('Remove old dates logic comes here!');
  }).catch(error => {
    console.log(error);
  });
};

function getYearMonthDayString(date) {
  let day = date.getDate();
  day = day < 10 ? '0' + day : day;
  let month = date.getMonth() + 1;
  month = month < 10 ? '0' + month : month;
  const year = date.getFullYear();

  return `${year}-${month}-${day}`;
}
