import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  constructor(
    public fb: FormBuilder,
    public authService: AuthService,
    public router: Router
  ) {
    this.registerForm = this.fb.group({
      email: [''],
      fullName: [''],
      password: [''],
      confirmPassword: [''],
      city: [''],
      address: [''],
      phone: [''],
    });
  }
  ngOnInit() {}
  registerUser() {
    this.authService.signUp(this.registerForm.value).subscribe((res) => {
      if (res.result) {
        this.registerForm.reset();
        this.router.navigate(['log-in']);
      }
    });
  }
}
