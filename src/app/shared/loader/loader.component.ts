import { Component, OnDestroy, OnInit } from '@angular/core';
import { LoaderService } from './loader.service';
import { Subscription } from 'rxjs/internal/Subscription';

@Component({
  selector: 'app-loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.css']
})
export class LoaderComponent implements OnInit, OnDestroy {

  show = false;
  loaderStateSubscription: Subscription;

  constructor(private loaderService: LoaderService) { }

  ngOnInit() {
    this.loaderStateSubscription = this.loaderService.loaderState.subscribe(show => {
      this.show = show;
    });
  }

  ngOnDestroy(): void {
    this.loaderStateSubscription.unsubscribe();
  }

}
