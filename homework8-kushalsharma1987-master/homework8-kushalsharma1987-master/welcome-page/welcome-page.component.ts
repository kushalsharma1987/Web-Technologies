import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.css']
})
export class WelcomePageComponent implements OnInit {
  
  title = 'kshar19-forecast-hw8';
  
  constructor() { }

  ngOnInit() {
  }

}
