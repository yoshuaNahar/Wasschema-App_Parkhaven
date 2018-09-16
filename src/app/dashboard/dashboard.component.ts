import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MediaChange, ObservableMedia } from '@angular/flex-layout';
import { MatSidenav } from '@angular/material';
import { AuthService } from '../auth/auth.service';
import { SchemaService } from '../features/schema/schema.service';
import { SettingsService } from '../features/settings/settings.service';
import { combineLatest } from 'rxjs';
import { map } from 'rxjs/operators';
import { PushNotificationsService } from '../features/settings/push-notifications/push-notifications.service';
import { Subscription } from 'rxjs/internal/Subscription';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  sideNavMode;
  sideNavOpened;

  userData;
  loggedInUser;

  private isMobile: boolean; // xs

  @ViewChild(MatSidenav) private sideNav: MatSidenav;

  private megaSubscription: Subscription;
  private randomSubscription: Subscription;
  private observableMediaSubscription: Subscription;

  constructor(private router: Router,
              private authService: AuthService,
              private observableMedia: ObservableMedia,
              private schemaService: SchemaService,
              private settingsService: SettingsService,
              private pushService: PushNotificationsService) {
  }

  ngOnInit() {
    this.observableMediaSubscription = this.observableMedia.subscribe((media: MediaChange) => {
      if (media.mqAlias === 'xs') {
        this.isMobile = true;
        this.sideNavMode = 'over';
        this.sideNavOpened = false;
      } else {
        this.isMobile = false;
        this.sideNavMode = 'side';
        this.sideNavOpened = true;
      }
    });

    this.loggedInUser = this.authService.getCurrentSignedInUser();

    // this.pushService.listen(); // listen to push notifications

    this.megaSubscription = combineLatest(
      this.authService.fetchUserInformation().valueChanges(),
      this.schemaService.onInitFetchMachinesInfo(),
      // still an observable, but short processing
      this.schemaService.onInitFetchDays().pipe(
        map(documentChangeAction => {
          return documentChangeAction.map((doc: any) => {
            return {
              id: doc.payload.doc.id,
              isCurrentWeek: doc.payload.doc.data().isCurrentWeek,
              isDisplayable: doc.payload.doc.data().isDisplayable
            };
          }).filter(day => day.isDisplayable);
        }))
    ).subscribe(resultArray => {

      // The first forkJoin Observable ==
      this.userData = resultArray[0]; // admin field
      console.log(this.userData);

      // The second forkJoin Observable ==
      // This is needed at the time that the schemaComponent starts.
      // This is the best place after the login
      resultArray[1].forEach(doc => {
        const data = doc.data();

        this.schemaService.machinesInfo.push({
          id: doc.id,
          type: data.type,
          room: data.room
        });
      });

      // The third forkJoin Observable ==
      this.schemaService.days = resultArray[2];
      console.log(this.schemaService.days);
      this.schemaService.daysChanged.next([...this.schemaService.days]);

      // This will be finished in the end
      this.randomSubscription = this.settingsService.fetchFavouriteLaundryRoom().subscribe((userInfoDoc: any) => {
        const userInfo = userInfoDoc.data();
        console.log('fethingFavouriteRoom and navigating: {}', userInfo);
        if (userInfo.favouriteRoom !== null) { // TODO: remove this if after development. Everyone will have a favouriteRoom by default.
          this.router.navigate(['room', userInfo.favouriteRoom]);
        }
      });
    }, err => {console.log(err)});
  }

  ngOnDestroy(): void {
    this.megaSubscription.unsubscribe();
    this.randomSubscription.unsubscribe();
    this.observableMediaSubscription.unsubscribe();
    this.schemaService.daysChanged.unsubscribe();
  }

  closeSideNav() {
    if (this.isMobile) {
      this.sideNav.close();
    }
  }

  logout() {
    this.authService.logout();
  }

}
