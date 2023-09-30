import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home/home.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'home',
  },
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'login',
    // component: LoginComponent,
  },
  {
    path: 'register',
    // component: RegisterComponent,
  },
  {
    path: 'about',
    // component: AboutComponent,
    pathMatch: 'full',
  },
  {
    path: 'locations',
    // component: LocationHomeComponent,
    pathMatch: 'full',
  },
  {
    path: 'add',
    // component: ProductCreateComponent,
    // canActivate: [AuthActivate],
    data: {
      authenticationRequired: true,
      authenticationFailureRedirectUrl: 'home',
    },
  },
  {
    path: 'confirm',
    // component: OrderConfirmComponent,
    // canActivate: [AuthActivate],
    data: {
      authenticationRequired: true,
      authenticationFailureRedirectUrl: 'login',
    },
  },
  {
    path: 'all',
    // component: ProductAllComponent,
    // canActivate: [AuthActivate],
    data: {
      authenticationRequired: true,
      authenticationFailureRedirectUrl: 'home',
    },
  },
  {
    path: 'reviews',
    // component: ReviewHomeComponent,
    // canActivate: [AuthActivate],
    data: {
      authenticationRequired: true,
      authenticationFailureRedirectUrl: 'home',
    },
  },
  {
    path: 'details/:productId',
    // component: ProductDetailsComponent,
    // canActivate: [AuthActivate],
    data: {
      authenticationRequired: true,
      authenticationFailureRedirectUrl: 'home',
    },
    pathMatch: 'full',
  },
  {
    path: 'profile',
    // component: ProfileComponent,
    // canActivate: [AuthActivate],
    data: {
      authenticationRequired: true,
      authenticationFailureRedirectUrl: 'login',
    },
  },
  {
    path: '**',
    // component: NotFoundComponent,
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
