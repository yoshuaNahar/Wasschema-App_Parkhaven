#### Wasschema2 - Laundry Schedule for Parkhaven

#### How to run

##### Front end
- Serve:`ng serve --aot --host 0.0.0.0`
- Build: `ng build --aot --prod`
- Deploy: `firebase deploy --only hosting`
  
##### Back end
- Deploy: `cd functions && firebase deploy --only functions`

TODO: also provide details of cronjobs

#### How to upgrade

When adding or changing features also upgrade your project.
Run `npm outdated` to see what's updatable. A quick way to update
everything is to run `ncu -u` `npm update` `npm install`. Something
will most likely break, so manually test the app locally and run all tests 
[(source)](https://flaviocopes.com/update-npm-dependencies/).

#### Tools:
- Backend - Firebase (auth + firestore + messaging + cloud functions)
- Frontend - Angular (material + ng-flex + angularFire)


- I did not make use of HammerJS for the swipe functionality for the sidenav

#### Motivation

This project was intended to be a hobby project to learn Java EE.
I quickly realized that using Java EE is (or was) very cumbersome,
so I moved to Spring. The first version of the app was build with 
JSP, Servlets and Spring JDBC. When realizing that Servlets are embarrassingly
old I updated the app to use Spring MVC. The latest version was made in Angular and Firebase
to test the latest development tools out there.

#### Note
Create your own environment & firebase-messaging-sw files when pulling.
 
