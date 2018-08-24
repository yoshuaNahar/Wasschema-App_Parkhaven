const admin = require('firebase-admin');

module.exports.appointmentRef = appointment => {
  return admin.firestore().collection('appointments').doc(
    `${appointment.day.value}_${appointment.time.index}_${appointment.machineInfo.room.id}_${appointment.machineInfo.id}`);
};

module.exports.userRef = houseNumber => {
  return admin.firestore().collection('users').doc(houseNumber);
};

module.exports.setCorrectCounterType = (dayDocument, appointment) => {
  if (appointment.machineInfo.type !== 'laundrymachine' &&
    appointment.machineInfo.type !== 'dryer') {
    appointment.machineInfo.type = 'laundrymachine';
  }

  if (!dayDocument.isCurrentWeek) {
    if (appointment.machineInfo.type === 'laundrymachine') {
      console.log('here2', appointment.machineInfo.type);
      return 'nextWeekLaundrymachine';
    } else if (appointment.machineInfo.type === 'dryer') {
      console.log('here3', appointment.machineInfo.type);
      return 'nextWeekDryer';
    }
  }

  return appointment.machineInfo.type;
};

// TODO: I could reuse this again, since I use it to add and remove an appointment
