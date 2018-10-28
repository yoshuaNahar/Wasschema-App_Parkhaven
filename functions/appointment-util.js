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

module.exports.getYearMonthDayString = (date) => {
  let day = date.getDate();
  day = day < 10 ? '0' + day : day;
  let month = date.getMonth() + 1;
  month = month < 10 ? '0' + month : month;
  const year = date.getFullYear();

  return `${year}-${month}-${day}`;
};

module.exports.addPadding = (number) => {
  return number < 10 ? '0' + number : '' + number; // ('' + month) for string result
};
