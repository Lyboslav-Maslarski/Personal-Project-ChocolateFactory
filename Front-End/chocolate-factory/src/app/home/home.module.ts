import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonsModule } from '../commons/commons.module';

@NgModule({
  declarations: [HomeComponent, AboutComponent],
  imports: [CommonModule, NgbCarouselModule, CommonsModule],
  exports: [HomeComponent, AboutComponent],
})
export class HomeModule {}
