import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';
import { RouterModule } from '@angular/router';
import {ReactiveFormsModule} from '@angular/forms';

@NgModule({
  imports: [],
  exports: [
    CommonModule,
    RouterModule,
    BrowserAnimationsModule,
    MaterialModule,
    ReactiveFormsModule,
  ],
  declarations: []
})
export class SharedModule {
}
