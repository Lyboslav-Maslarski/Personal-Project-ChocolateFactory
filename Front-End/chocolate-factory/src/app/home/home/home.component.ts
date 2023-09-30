import { Component } from '@angular/core';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [NgbCarouselConfig],
})
export class HomeComponent {
  images = [
    'https://res.cloudinary.com/dmncdcgx9/image/upload/v1696043900/footerBackground_mbyuup.jpg',
    'https://res.cloudinary.com/dmncdcgx9/image/upload/v1696045070/whiteTopTransparent_kz6jbx.png',
  ];

  constructor(config: NgbCarouselConfig) {
    // customize default values of carousels used by this component tree
    config.interval = 2000;
    config.keyboard = true;
    config.pauseOnHover = true;
  }
}
