import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [HomeComponent, AboutComponent],
  imports: [CommonModule,NgbCarouselModule],
  exports: [HomeComponent, AboutComponent],
})
export class HomeModule {}
