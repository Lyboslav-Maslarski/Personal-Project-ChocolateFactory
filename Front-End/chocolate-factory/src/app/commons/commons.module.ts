import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { RouterModule } from '@angular/router';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [FooterComponent, HeaderComponent, AdminPanelComponent],
  imports: [CommonModule, RouterModule, NgbDropdownModule],
  exports: [FooterComponent, HeaderComponent],
})
export class CommonsModule {}
