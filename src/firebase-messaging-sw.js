importScripts('https://www.gstatic.com/firebasejs/5.5.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/5.5.0/firebase-messaging.js');

const config = {
  apiKey: 'AIzaSyBN5qsGqVAzWwOgnYYToNFOYcLvCE0zXM0',
  authDomain: 'fir-531f4.firebaseapp.com',
  databaseURL: 'https://fir-531f4.firebaseio.com',
  projectId: 'fir-531f4',
  storageBucket: 'fir-531f4.appspot.com',
  messagingSenderId: '932886716822'
};

firebase.initializeApp(config);
firebase.messaging();
// Everything above is needed for a push notification to shown or sent to the messaging.onMessage subscription
