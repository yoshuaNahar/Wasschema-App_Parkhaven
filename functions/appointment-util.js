module.exports.appointmentSpotRef = (appointment, admin, appointmentValue) => {
  return admin.firestore().collection('rooms')
  .doc(appointment.room).update({[`${appointment.machineMetaData.name}.${appointment.day}.${appointment.time.index}`]: appointmentValue});
};

module.exports.userCounterRef = (houseNumber, type, admin) => {
  return admin.database().ref(`users/${houseNumber}/counters/${type}`);
};

// TODO: I could reuse this again, since I use it to add and remove an appointment
