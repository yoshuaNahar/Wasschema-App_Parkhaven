// Weekly
// days collection: - Update isCurrentWeek
//                  - Remove previousWeek
//                  - Create new nextWeek
// users collection: - Of each userData, set nextWeekCounters to 0 and move the values of nextWeekCounters to the currentWeek
// appointments collection: - Remove appointments with dates that are removed
const admin = require('firebase-admin');
const appUtil = require('./appointment-util');

exports.handler = function (request, response) {
  let daysToRemove = [];
  let daysChangeIsCurrentWeek = [];
  let lastDay;
  const appointmentsCollection = admin.firestore().collection('appointments');
  const daysCollection = admin.firestore().collection('days');

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

    daysToRemove.forEach(day => {
      batch.delete(daysCollection.doc(day.id));
    });

    daysChangeIsCurrentWeek.forEach(day => {
      batch.update(daysCollection.doc(day.id), {
        isCurrentWeek: true
      });
    });

    // create 7 days after the last date in the days collection
    const lastDayDate = new Date(lastDay.id);
    console.log(lastDayDate);
    const daysToCreate = [];
    for (let i = 0; i < 7; i++) {
      lastDayDate.setDate(lastDayDate.getDate() + 1);
      // console.log('forLoop', lastDayDate);
      daysToCreate.push(appUtil.getYearMonthDayString(lastDayDate));
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
      const accountExists = Object.keys(userDoc.data()).length > 0; // object that is not this: { }
      if (accountExists) {
        users.push({id: userDoc.id, data: userDoc.data()});
      }
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
    // Remove old appointments
    const lastDateIndex = 6;
    const lastDate = daysToRemove[lastDateIndex];
    // console.log('lastDate', lastDate);

    return appointmentsCollection.where("date", "<=", lastDate.id).get();
  }).then(appointmentQueries => {
    const batch = admin.firestore().batch();

    appointmentQueries.forEach(doc => {
      console.log('doc', doc.id);
      batch.delete(appointmentsCollection.doc(doc.id));
    });

    return batch.commit();
  }).then(() => {
    response.status(200).send('Remove old dates logic comes here!');
  }).catch(error => {
    response.status(400).send({message: error.message});
  });
};
