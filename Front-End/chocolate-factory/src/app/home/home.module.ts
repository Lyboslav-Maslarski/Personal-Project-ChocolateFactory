import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonsModule } from '../commons/commons.module';
import { ContactComponent } from './contact/contact.component';
import { FormsModule } from '@angular/forms';
import { MaintenanceComponent } from './maintenance/maintenance.component';

@NgModule({
  declarations: [HomeComponent, AboutComponent, ContactComponent, MaintenanceComponent],
  imports: [CommonModule, NgbCarouselModule, CommonsModule, FormsModule],
  exports: [HomeComponent, AboutComponent],
})
export class HomeModule {}
