// Daily + When the admin adds a maintenance
/*
 * Steps:
 * 1. Get all the maintenances
 * 2. Remove maintenances which have already passed
 * 3. Of each userData which has an appointment on a date that is marked,
 *    give them their counter back.
 * 4. Mark the dates where the maintenaces are active as not usable
 */

const admin = require('firebase-admin');
const appUtil = require('./appointment-util');

let days;
let machinesInfo;

exports.handler = function (request, response) {
  const batch = admin.firestore().batch();

  // get all days data
  admin.firestore().collection('days').get().then(querySnapshot => {
    days = querySnapshot.docs.map(doc => {
      return {
        id: doc.id,
        isCurrentWeek: doc.data().isCurrentWeek
      };
    });

    // get all machinesInfo data
    return admin.firestore().collection('machinesInfo').get();
  }).then(querySnapshot => {
    machinesInfo = querySnapshot.docs.map(doc => {
      return {
        id: doc.id,
        type: doc.data().type
      };
    });

    return admin.firestore().collection('maintenances').get();
  }).then(querySnapshot => {
    const functionPromises = [];

    // all the logic of of a maintenance is here
    querySnapshot.forEach(doc => {
      const maintenance = {
        id: doc.id,
        room: doc.data().room,
        machines: doc.data().machines,
        startingDate: doc.data().startingDate,
        startingTime: doc.data().startingTime,
        endingDate: doc.data().endingDate,
        endingTime: doc.data().endingTime,
        reason: doc.data().reason
      };
      console.log('maintenance', maintenance);

      const endingDate = new Date(maintenance.endingDate);
      const today = new Date();

      if (isPassed(endingDate, today)) {
        batch.delete(admin.firestore().collection('maintenances')
          .doc(maintenance.id));
        console.log('deleted maintenance', maintenance.id);
      } else {
        functionPromises
          .push(getAppointmentsInTheMaintenanceTime(maintenance, batch));
      }
    });
    return Promise.all(functionPromises);
  }).then(() => {
    batch.commit();
    response.status(200).send('OK');
  });
};

function getAppointmentsInTheMaintenanceTime(maintenance, batch) {
  const functionPromises = [];

  maintenance.machines.forEach(machine => {
    console.log('machine', machine);

    const functionPromise = admin.firestore().collection('appointments')
      .where('date', '>=', maintenance.startingDate)
      .where('date', '<=', maintenance.endingDate)
      .where('machine', '==', machine)
      .get()
      .then(querySnapshot => {
        // used below to make sure all the userData async code is done
        const userPromises = [];

        querySnapshot.docs.forEach(doc => {
          // appointments arent from specific times, so I need to check the
          // starting and ending time of maintenances
          const appointment = {id: doc.id, data: doc.data()};
          if ((appointment.data.date === maintenance.startingDate &&
            appointment.data.time < maintenance.startingTime) ||
            (appointment.data.date === maintenance.endingDate) &&
            (appointment.data.time > maintenance.endingTime) ||
            appointment.data.houseNumber === undefined) {
            return;
          }

          appointment.data.machineInfo = machinesInfo.find(machine => {
            return machine.id === appointment.data.machine;
          });
          console.log('appointment', appointment);

          const dayOfThisAppointment = days.find(day => {
            return day.id === appointment.data.date;
          });
          console.log('dayOfThisAppointment', dayOfThisAppointment);

          const counterType = appUtil
            .setCorrectCounterType(dayOfThisAppointment, appointment.data);
          console.log('counterType', counterType);

          const userPromise = admin.firestore().collection('users')
            .doc(appointment.data.houseNumber).get()
            .then(doc => {
              const user = {id: appointment.data.houseNumber, data: doc.data()};
              console.log('user', user);
              admin.firestore().collection('users').doc(user.id).update(
                {
                  ['counters.' + counterType]: user.data.counters[counterType]
                  + 1
                });

              admin.firestore().collection('appointments').doc(appointment.id)
                .delete();
              return Promise.resolve();
            });
          userPromises.push(userPromise);
        });

        return Promise.all(userPromises);
      }).then(() => {
        // add all the appointments with actual maintenance message
        const dates = calculateDateTimes(maintenance);
        dates.forEach(date => {
          console.log('date', date);
          batch.set(admin.firestore().collection('appointments')
              .doc(`${date.date}_${date.time}_${maintenance.room}_${machine}`),
            {
              date: date.date,
              time: date.time,
              machine: machine,
              room: maintenance.room
            });
        });
      });
    functionPromises.push(functionPromise);
    // end of maintenance machines for loop
  });
  return Promise.all(functionPromises);
}

function calculateDateTimes(maintenance) {
  const datesAndTimes = [];

  const currentDate = new Date(maintenance.startingDate);
  const endDate = new Date(maintenance.endingDate);

  let startingTime = maintenance.startingTime;

  while (true) {
    for (let i = startingTime; i < 13; i++) {
      console.log(currentDate, i);
      if (sameDate(currentDate, endDate)) {
        console.log('here');
        console.log(i, maintenance.endingTime);
        if (i > maintenance.endingTime) {
          return datesAndTimes;
        }
      }

      const dateAsString = currentDate.getFullYear() + '-'
        + getMonth(currentDate)
        + "-" + currentDate.getDate();
      datesAndTimes.push({date: dateAsString, time: i})
    }
    startingTime = 0; // only the first day should the starting time not be 0;
    currentDate.setDate(currentDate.getDate() + 1);
  }
}

function sameDate(d1, d2) {
  return d1.getFullYear() === d2.getFullYear() &&
    d1.getMonth() === d2.getMonth() &&
    d1.getDate() === d2.getDate();
}

function isPassed(d1, d2) {
  console.log(d1);
  console.log(d2);
  console.log(d1.getFullYear());
  console.log(d2.getFullYear());
  console.log(d1.getMonth());
  console.log(d2.getMonth());
  console.log(d1.getDate());
  console.log(d2.getDate());
  return d1.getFullYear() <= d2.getFullYear() &&
    d1.getMonth() <= d2.getMonth() &&
    d1.getDate() < d2.getDate();
}

function getMonth(date) {
  const month = date.getMonth() + 1;
  return month < 10 ? '0' + month : '' + month; // ('' + month) for string result
}
