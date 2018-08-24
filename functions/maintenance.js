const admin = require('firebase-admin');

exports.handler = function (request, response) {
  admin.firestore().collection('maintenances').get().then(querySnapshot => {
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

      if (isPassed(new Date(maintenance.endingDate), new Date())) {
        admin.firestore().collection('maintenances')
          .doc(maintenance.id).delete();
        console.log('deleted maintenance', maintenance.id);
      } else {

        maintenance.machines.forEach(machine => {
          admin.firestore().collection('maintenances').doc(
            maintenance.room).get().then(doc => {
            console.log('doc.data()', doc.data());
            const roomData = doc.data();

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

                  admin.firestore().collection('users').doc(houseNumber).update(
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
        });
      }
    });
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
