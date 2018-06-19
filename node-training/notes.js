console.log('Starting notes.js');

// console.log(module);

module.exports.addNote = function() {
  console.log('add note');
  return 'New note';
};

module.exports.add = (x, y) => {
  return x + y;
};
