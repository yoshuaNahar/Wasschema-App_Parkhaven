# Firebase Cloud Functions

Setup Firebase cloud functions as explained here: https://firebase.google.com/docs/functions/get-started

# How to run
- `firebase deploy --only functions`

# How to test
- `firebase serve --only functions`

<b>Note</b>: serving locally only works with Node 6.11.5 and not with 10.x.x (versions in between not tested)

## The admin features
All the admin features will be done in the frontend.
It will be locked by the security rules.

===

weakly-cleanup works. It should be done every sunday night
 
TODO: Fix at home, test:
 - daily-handle-maintenance

The reason that add-appointment etc, needs to be in a cloud function
is because I also need to increment the counter for the users.
With add-maintenance and remove user as an admin, it is not the case.
