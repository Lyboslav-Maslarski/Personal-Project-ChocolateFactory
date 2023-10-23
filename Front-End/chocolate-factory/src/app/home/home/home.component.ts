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
    'https://res.cloudinary.com/lyb4ooo/image/upload/v1696099200/carousel-01_ybg9xi.jpg',
    'https://res.cloudinary.com/lyb4ooo/image/upload/v1696099211/carousel-02_zwmu2c.jpg',
    'https://res.cloudinary.com/lyb4ooo/image/upload/v1696099212/carousel-03_o6v6it.jpg',
  ];
  constructor(config: NgbCarouselConfig) {
    config.interval = 5000;
    config.wrap = true;
    config.keyboard = true;
    config.pauseOnHover = true;
  }
}
