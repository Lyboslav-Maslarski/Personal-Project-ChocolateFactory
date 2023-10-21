import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { RouterModule } from '@angular/router';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';

@NgModule({
  declarations: [FooterComponent, HeaderComponent, AdminPanelComponent],
  imports: [CommonModule, RouterModule],
  exports: [FooterComponent, HeaderComponent],
})
export class CommonsModule {}
