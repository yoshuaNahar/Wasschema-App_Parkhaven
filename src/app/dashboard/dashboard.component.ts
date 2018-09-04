import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MediaChange, ObservableMedia } from '@angular/flex-layout';
import {  MatSidenav } from '@angular/material';
import { AuthService } from '../auth/auth.service';
import { SchemaService } from '../features/schema/schema.service';
import { SettingsService } from '../features/settings/settings.service';

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

    this.authService.fetchUserInformation().valueChanges().subscribe((user: { admin }) => {
      this.userData = user;
      console.log(this.userData);

      // TODO: I should let this wait on every other subscription in here, maybe use RXJS pipe and switchmap?
      this.settingsService.fetchFavouriteLaundryRoom().subscribe((userInfo:any) => {
        console.log('fethingFavouriteRoom and navigating: {}', userInfo);
        this.router.navigate(['room', userInfo.favouriteRoom]);
      });
    });

    // This is needed at the type that the schemaComponent starts.
    // This is the best place after the login
    this.schemaService.onInitFetchMachinesInfo();

    // Otherwise the response is later than the params observable
    this.schemaService.onInitFetchDays();
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
