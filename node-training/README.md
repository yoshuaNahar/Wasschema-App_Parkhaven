# The Complete NodeJS Developer Course

Demo code from following the Udemy Course.

# Notes
Installed nodemon `npm install nodemon -g`.
Use it by using nodemon `main-js-file.js`.
It will automatically restart JavaScript files for you.

# Important Parts
Section 3. Part 20. Debugging Node.js Applications
Section 3. Part 21. Debugging via Chrome Dev Tools
Section 3. Part 24. Arrow Functions
```
var user = {
  name: 'Jason',
  sayHi: () => {
    console.log(arguments);
    console.log(`Hi. I'm ${this.name}`);
  },
  sayHiAlt() {
    console.log(arguments);
    console.log(`Hi. I'm ${this.name}`);
  }
}
user.sayHi();
user.sayHiAlt(1, 2);

// output
Global module object.
Hi. I'm undefined. (Global varibale used)
{ '0': 1, '1': 2 }
Hi. I'm Jason
```

Pretty Print JSON Objects to the console by using `console.log(JSON.stringify(body, undefined, 2));nm `
