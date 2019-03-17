// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  firebaseConfig: {
    apiKey: "AIzaSyCOeD3vleDN99PnvWIsAsB5vhLtwQnE3wo",
    authDomain: "parkhaven-457c4.firebaseapp.com",
    databaseURL: "https://parkhaven-457c4.firebaseio.com",
    projectId: "parkhaven-457c4",
    storageBucket: "parkhaven-457c4.appspot.com",
    messagingSenderId: "271910223877"
  },
  firebaseUrl: 'http://localhost:5000/parkhaven-457c4/fill_extra_here'
};


