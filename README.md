#### Wasschema2 - Laundry Schedule for Parkhaven

#### How to run

- `ng serve --aot --host 0.0.0.0`
- Build: ng build --aot --prod
- Deploy: 
  - firebase deploy --only hosting
  - cd functions && firebase deploy --only functions

TODO: also provide details of cronjobs

#### How to upgrade

When adding or changing a feature try to also upgrade your project.
Run `npm outdated` to see what's updatable. An quick way to update
everything is to run `ncu -u` `npm update` `npm install`, but something
will most likely break, so try the app locally and run all tests. [source](https://flaviocopes.com/update-npm-dependencies/)

##### Used:
- Backend - Firebase (auth + firestore + messaging + cloud functions)
- Frontend - Angular (material + ng-flex + angularFire)


- I did not make use of HammerJS for the swipe 
  functionality for the sidenav

##### Motivation

This project was initially a hobby project to learn ~~Java EE~~ Spring.

Tools used:
* Backend - SpringMVC ~~Servlets~~ & JSP
* Database - Spring JDBC ~~JDBC raw~~ + MySQL
* Build tool - Maven
* IDE - InteliJ ~~Eclipse~~
* Container - Tomcat 8
* Server - Ubuntu
* Frontend - Bootstrap
