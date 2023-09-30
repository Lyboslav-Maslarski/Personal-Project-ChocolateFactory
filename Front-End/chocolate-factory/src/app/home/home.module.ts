import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AboutComponent } from './about/about.component';
import { HomeComponent } from './home/home.component';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [HomeComponent, AboutComponent],
  imports: [CommonModule, BrowserModule, FormsModule, NgbModule],
  bootstrap: [HomeComponent],
  exports: [HomeComponent],
})
export class HomeModule {}
