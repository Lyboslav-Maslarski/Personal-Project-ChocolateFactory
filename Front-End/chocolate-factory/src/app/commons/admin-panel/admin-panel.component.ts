import { Component, OnInit } from '@angular/core';
import { NgbActiveOffcanvas } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css'],
})
export class AdminPanelComponent implements OnInit {
  showAdminPart: boolean = false;

  constructor(public activeOffcanvas: NgbActiveOffcanvas) {}

  ngOnInit(): void {
    const roles = localStorage.getItem('roles');
    if (roles?.includes('ROLE_ADMIN')) {
      this.showAdminPart = true;
    }
  }
}
