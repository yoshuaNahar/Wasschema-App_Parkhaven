console.log('Starting app!');

const fs = require('fs');
const os = require('os');
const notes = require('./notes');
const _ = require('lodash');

let user = os.userInfo();

let fileData = `Hello ${user.username}. You are ${notes.age} years old.`;

console.log(_.uniq(['Mike', 1, 3, 2, 'Mike', 2]));
console.log(_.isString(true));
console.log(_.isString('Asd'));

// console.log(notes.addNote());
// console.log('5 + 9 =', notes.add(5, 9));

// fs.appendFile('greetings.txt', fileData, error => {
//   console.log(error)
// });
