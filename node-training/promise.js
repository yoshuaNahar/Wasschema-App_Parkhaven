const somePromise = new Promise((resolve, reject) => {
  setTimeout(() => {
    resolve('Hey. It worked!');
    reject('Unable to fulfill promise');
  }, 2500);
});

somePromise.then(success => {
  console.log('Success:', success);
}, error => {
  console.log('Error:', error);
});
