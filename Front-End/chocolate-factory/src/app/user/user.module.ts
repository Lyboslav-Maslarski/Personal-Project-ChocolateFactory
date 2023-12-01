import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import { FormsModule } from '@angular/forms';
import { CommonsModule } from '../commons/commons.module';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    ChangePasswordComponent,
    EditProfileComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    CommonsModule,
    ProfileComponent,
    RouterModule,
  ],
  exports: [
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    ChangePasswordComponent,
    EditProfileComponent,
  ],
})
export class UserModule {}
