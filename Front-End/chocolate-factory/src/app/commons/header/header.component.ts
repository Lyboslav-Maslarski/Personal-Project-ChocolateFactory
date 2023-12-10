import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { NgbOffcanvas } from '@ng-bootstrap/ng-bootstrap';
import { AdminPanelComponent } from '../admin-panel/admin-panel.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  showAdminBtn: boolean = false;
  showContactBtn: boolean = false;
  collapsed: boolean = true;

  constructor(
    public authService: AuthService,
    private offcanvasService: NgbOffcanvas
  ) {}

  ngOnInit(): void {
    const roles = localStorage.getItem('roles');
    if (roles) {
      this.showContactBtn = true;
    }
    if (roles?.includes('ROLE_ADMIN') || roles?.includes('ROLE_MODERATOR')) {
      this.showAdminBtn = true;
    }
  }

  logout() {
    this.authService.doLogout();
  }

  open() {
    this.offcanvasService.open(AdminPanelComponent);
  }
}
