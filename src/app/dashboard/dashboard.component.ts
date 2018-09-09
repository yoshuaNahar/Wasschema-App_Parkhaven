import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MediaChange, ObservableMedia } from '@angular/flex-layout';
import { MatSidenav } from '@angular/material';
import { AuthService } from '../auth/auth.service';
import { SchemaService } from '../features/schema/schema.service';
import { SettingsService } from '../features/settings/settings.service';
import { combineLatest } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  sideNavMode;
  sideNavOpened;

  userData;
  loggedInUser;

  private isMobile: boolean; // xs

  @ViewChild(MatSidenav) private sideNav: MatSidenav;

  constructor(private router: Router,
              private authService: AuthService,
              private observableMedia: ObservableMedia,
              private schemaService: SchemaService,
              private settingsService: SettingsService) {
  }

  ngOnInit() {
    this.observableMedia.subscribe((media: MediaChange) => {
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

    combineLatest(
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
      this.settingsService.fetchFavouriteLaundryRoom().subscribe((userInfo: any) => {
        console.log('fethingFavouriteRoom and navigating: {}', userInfo);
        this.router.navigate(['room', userInfo.favouriteRoom]);
      });
    }, err => {console.log(err)});
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
