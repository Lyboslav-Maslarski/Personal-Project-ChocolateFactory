import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonsModule } from '../commons/commons.module';
import { RouterModule } from '@angular/router';
import { ContactComponent } from './contact/contact.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [HomeComponent, AboutComponent, ContactComponent],
  imports: [CommonModule, NgbCarouselModule, CommonsModule, RouterModule, FormsModule],
  exports: [HomeComponent, AboutComponent],
})
export class HomeModule {}
