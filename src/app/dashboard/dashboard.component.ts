import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AngularFireAuth } from 'angularfire2/auth';
import { MediaChange, ObservableMedia } from '@angular/flex-layout';
import { MatDrawer } from '@angular/material';
import { AngularFirestore } from 'angularfire2/firestore';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  sideNavMode;
  sideNavOpened;

  private isMobile: boolean; // xs

  @ViewChild(MatDrawer) private sideNav: MatDrawer;

  constructor(private router: Router,
              private afAuth: AngularFireAuth,
              private observableMedia: ObservableMedia,
              private fbStore: AngularFirestore) {
  }

  ngOnInit() {
    this.fbStore.collection('rooms').doc('A').valueChanges().subscribe(asd => {
      console.log(asd);
    });

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
  }

  closeSideNav() {
    if (this.isMobile) {
      this.sideNav.close();
    }
  }

  logout() {
    this.afAuth.auth.signOut().then(() => {
      this.router.navigate(['/login']);
    });
  }

}
