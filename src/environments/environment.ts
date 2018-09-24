// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  firebaseConfig: {
    apiKey: 'AIzaSyBN5qsGqVAzWwOgnYYToNFOYcLvCE0zXM0',
    authDomain: 'fir-531f4.firebaseapp.com',
    databaseURL: 'https://fir-531f4.firebaseio.com',
    projectId: 'fir-531f4',
    storageBucket: 'fir-531f4.appspot.com',
    messagingSenderId: '932886716822'
  },
  // firebaseUrl: 'http://localhost:5000/fir-531f4/us-central1'
  firebaseUrl: 'https://us-central1-fir-531f4.cloudfunctions.net'
};
