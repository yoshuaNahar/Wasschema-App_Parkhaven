import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { NavigationEnd, Router, RouterLinkActive } from '@angular/router';
import { MediaChange, ObservableMedia } from '@angular/flex-layout';
import { MatSidenav, MatSnackBar } from '@angular/material';
import { AuthService } from '../auth/auth.service';
import { SchemaService } from '../features/schema/schema.service';
import { SettingsService } from '../features/settings/settings.service';
import { combineLatest } from 'rxjs';
import { map } from 'rxjs/operators';
import { Subscription } from 'rxjs/internal/Subscription';
import { DashboardService } from './dashboard.service';
import { AngularFireMessaging } from '@angular/fire/messaging';

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

  @ViewChild("rla") private rla: RouterLinkActive;

  schemaRouterLinkActive;

  // If a new notification has been added to the notification board
  isNewNotificationAvailable: boolean = false;

  private megaSubscription: Subscription;
  private observableMediaSubscription: Subscription;
  private messagingSubscription: Subscription;
  private newMessageInNotificationBoardSubscription: Subscription;

  constructor(private router: Router,
              private authService: AuthService,
              private observableMedia: ObservableMedia,
              private schemaService: SchemaService,
              private settingsService: SettingsService,
              private dashboardService: DashboardService,
              private afMessaging: AngularFireMessaging,
              private snackBar: MatSnackBar) {
    // bad practice, but put this in the ngOnInit and it won't fire the first time...
    this.setSchemaRouterLinkActiveWhenOnARoom();
  }

  ngOnInit() {
    this.screenHandler();

    this.loggedInUser = this.authService.getCurrentSignedInUser();

    this.messagingSubscription = this.afMessaging.messages.subscribe((message: any) => {
      this.snackBar.open(message.data.text, 'OK', {duration: 30000});
    }, () => {
      // swallow error when notifications blocked
    });

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
          room: data.room,
          color: data.color
        });
      });

      // The third forkJoin Observable ==
      this.schemaService.days = resultArray[2];
      console.log(this.schemaService.days);
      this.schemaService.daysChanged.next([...this.schemaService.days]);
    }, err => {
      console.log(err)
    });

    this.newMessageInNotificationBoardSubscription = this.settingsService.fetchPublicUserInfoRoom()
      .subscribe((userDataDoc: any) => {
        const userData = userDataDoc.data();
        if (userData.isNewNotificationAvailable) {
          this.isNewNotificationAvailable = userData.isNewNotificationAvailable;
        }
      });
  }

  ngOnDestroy(): void {
    this.megaSubscription.unsubscribe();
    this.observableMediaSubscription.unsubscribe();
    this.messagingSubscription.unsubscribe();
    this.newMessageInNotificationBoardSubscription.unsubscribe();
  }

  closeSideNav() {
    if (this.isMobile) {
      this.sideNav.close();
    }
  }

  logout() {
    this.authService.logout();
  }

  private setSchemaRouterLinkActiveWhenOnARoom() {
    this.router.events.subscribe(ev => {
      if (ev instanceof NavigationEnd) {
        if (ev.url.startsWith('/room/') || ev.url === '/') {
          this.schemaRouterLinkActive = true;
        }
      } else {
        this.schemaRouterLinkActive = false;
      }
    });
  }

  private screenHandler() {
    this.isMobile = this.dashboardService.dashboardScreen.isMobile;
    this.sideNavMode = this.dashboardService.dashboardScreen.sideNavMode;
    this.sideNavOpened = this.dashboardService.dashboardScreen.sideNavOpened;

    // If you log off and log back in without reloading, this does not run. Only when you change
    // The screen size. That is why I do the workaround with the dashboardService
    this.observableMediaSubscription = this.observableMedia.subscribe((media: MediaChange) => {
      if (media.mqAlias === 'xs') {
        this.isMobile = true;
        this.sideNavMode = 'over';
        this.sideNavOpened = false
      } else {
        this.isMobile = false;
        this.sideNavMode = 'side';
        this.sideNavOpened = true;
      }
    });

    this.dashboardService.dashboardScreen = {
      isMobile: this.isMobile,
      sideNavMode: this.sideNavMode,
      sideNavOpened: this.sideNavOpened
    };
  }

}
