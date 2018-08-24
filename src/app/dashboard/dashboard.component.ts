import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MediaChange, ObservableMedia } from '@angular/flex-layout';
import { MatDrawer } from '@angular/material';
import { AuthService } from '../auth/auth.service';
import { SchemaService } from '../features/schema/schema.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  sideNavMode;
  sideNavOpened;

  user;

  private isMobile: boolean; // xs

  @ViewChild(MatDrawer) private sideNav: MatDrawer;

  constructor(private router: Router,
              private authService: AuthService,
              private observableMedia: ObservableMedia,
              private schemaService: SchemaService) {
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

    this.authService.fetchUserInformation().valueChanges().subscribe((user: { admin }) => {
      this.user = user;
      console.log(this.user);
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
