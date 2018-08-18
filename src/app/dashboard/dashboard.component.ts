import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AngularFireAuth } from 'angularfire2/auth';
import { MediaChange, ObservableMedia } from '@angular/flex-layout';
import { MatDrawer } from '@angular/material';
import { AngularFirestore } from 'angularfire2/firestore';
import { AuthService } from '../auth/auth.service';

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
              private observableMedia: ObservableMedia) {
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

    this.authService.getUserInformation().valueChanges().subscribe((user: {admin}) => {
      this.user = user;
      console.log(this.user);
    });
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
