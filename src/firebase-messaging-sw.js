importScripts('https://www.gstatic.com/firebasejs/5.5.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/5.5.0/firebase-messaging.js');

const config = {
    apiKey: "AIzaSyCOeD3vleDN99PnvWIsAsB5vhLtwQnE3wo",
    authDomain: "parkhaven-457c4.firebaseapp.com",
    databaseURL: "https://parkhaven-457c4.firebaseio.com",
    projectId: "parkhaven-457c4",
    storageBucket: "parkhaven-457c4.appspot.com",
    messagingSenderId: "271910223877"
};

firebase.initializeApp(config);
firebase.messaging();
// Everything above is needed for a push notification to shown or sent to the messaging.onMessage subscription
