import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AngularFireAuth} from 'angularfire2/auth';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  sideNavMode;
  sideNavOpened;
  isMobile: boolean;

  constructor(private router: Router,
              private afAuth: AngularFireAuth) {
    this.isMobile = this.checkIfMobile();
    if (this.isMobile) {
      this.sideNavMode = 'over';
      this.sideNavOpened = false;
    } else {
      this.sideNavMode = 'side';
      this.sideNavOpened = true;
    }
  }

  ngOnInit() {
  }

  checkIfMobile() {
    console.log(window.innerWidth);

    return window.innerWidth <= 600;
  }

  logout() {
    this.afAuth.auth.signOut().then(() => {
      this.router.navigate(['/login']);
    });
  }

}
