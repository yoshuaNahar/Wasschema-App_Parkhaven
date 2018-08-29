// Daily + When the admin adds a maintenance

/*
 * Steps:
 * 1. Get all the maintenances
 * 2. Remove maintenances which have already passed
 * 3. Of each user which has an appointment on a date that is marked,
 *    give them their counter back.
 * 4. Mark the dates where the maintenaces are active as not usable
 */

const admin = require('firebase-admin');
const appUtil = require('appointment-util');

const maintenacesRef = admin.firestore().collection('maintenances');
const appointmentsRef = admin.firestore().collection('appointments');
const usersRef = admin.firestore().collection('users');
const batch = admin.firestore().batch();

const maintenances = [];

exports.handler = function (request, response) {
  maintenacesRef.get().then(querySnapshot => {
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
        maintenacesRef.doc(maintenance.id)
          .delete();
        console.firefirlog('deleted maintenance', maintenance.id);
      } else {
        maintenance.push(maintenance);

        admin.firestore().collection('days').get().then(querySnapshot => {
          const days = querySnapshot.docs.map(doc => {
            return {
              id: doc.id,
              isCurrentWeek: doc.data().isCurrentWeek,
            };
          });

          // TODO: 3. Remove all the appointments that are currently in the time of the maintenace
          maintenance.machines.forEach(machine => {
            console.log('machine', machine);
            admin.firestore().collection('machinesInfo').doc(machine).get
            appointmentsRef
              .where('date', '>=', maintenance.startingDate)
              .where('date', '<=', maintenance.endingDate)
              .where('time', '>=', maintenance.startingTime)
              .where('time', '<=', maintenance.endingTime)
              .where('machine', '==', machine).get().then(querySnapshot => {
              querySnapshot.docs.forEach(doc => {
                const appointment = {id: doc.id, data: doc.data()};

                const dayOfThisAppointment = days.find(day => {
                  return day.id === appointment.data.day;
                });

                const counterType = appUtil.setCorrectCounterType(dayDocument, appointment);
                const userDoc = usersRef.doc(appointment.houseNumber);
                // ALSO NEED TO GET THIS USER
                batch.update(userDoc, {counters: {}})

                const appointmentDoc = appointmentsRef.doc(appointment.id);
                batch.delete(appointmentDoc);
              });
            });

            var group1Ref = db.collection("chatroom").doc("group1");
            batch.set(group1Ref, {msg: "Hello, from Admin!"});

            var newUserRef = db.collection("users").doc("newuser");
            batch.update(newUserRef, {"lastSignedIn": new Date()});

            var removedAdminRef = db.collection("admin").doc("otheruser");
            batch.delete(removedAdminRef);

            const datesAndTimes = calculateDateTimes(maintenance);
            console.log('datesAndTimes', datesAndTimes);

            for (let dt of datesAndTimes) {

              const houseNumber = roomData[machine][dt.date][dt.time];
              console.log('dt, houseNumber', dt, houseNumber);
              if (houseNumber !== '-' || houseNumber !== '--') {
                // reset counter

                // TODO: This works. But how to remove the counters when I rewrite people appointments?
                // TODO: I think I should re-add counters when I encounter a persons houseNumber.

                // get all roomsInfo data
                // if not equal to - or -- change
                // change counter of housenumber on that spot
                admin.firestore().collection('users').doc(
                  houseNumber).get().then(doc => {
                  const user = doc.data();

                  // TODO: Use batch writes here.
                  usersRef.doc(houseNumber).update(
                    {
                      [`${machine}.${dt.date}.${dt.time}`]: '--'
                    });
                });

                // update appointment
                console.log('appointment position:',
                  `${machine}.${dt.date}.${dt.time}`);
                admin.firestore().collection('roomsInfo').doc(
                  maintenance.room).update({
                  [`${machine}.${dt.date}.${dt.time}`]: '--'
                });
              }
              // else continue
            }
          });
        }
      }
    );
  }).then(() => {
    response.status(200).send('OK');
  });
};

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
        if (i === maintenance.endingTime) {
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
