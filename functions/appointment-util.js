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
      return 'nextWeekLaundrymachine';
    } else if (appointment.machineInfo.type === 'dryer') {
      return 'nextWeekDryer';
    }
  }

  return appointment.machineInfo.type;
};
