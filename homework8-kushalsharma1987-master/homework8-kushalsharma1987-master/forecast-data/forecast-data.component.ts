import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { MatTabChangeEvent } from '@angular/material';

@Component({
  selector: 'forecast-data',
  templateUrl: './forecast-data.component.html',
  styleUrls: ['./forecast-data.component.css']
})
export class ForecastDataComponent implements OnInit {

  @Input() weatherData: any;
  @Input() weatherString: '';
  @Input() geocodeData: any;
  @Input() currlocData: any;
  @Input() cityValue: '';
  @Input() stateValue: '';
  @Input() imageString: '';
  favoriteBtn = false;
  @Output() selectedTabChange: EventEmitter<MatTabChangeEvent>
  weeklyTab = 0;
  // currentData: any = this.weatherData.currently;
  
  // weatherString = JSON.stringify(this.weatherData);
  // weatherData = JSON.parse(this.weatherString);

  // weatherFound: number = (<[]>this.weatherData).length;

  // dailyData: [] = this.weatherData.daily.data;
  
  constructor() { }

  ngOnInit() {
  //   if (localStorage.getItem(''))
  //   this.favoriteBtn
  // }
  this.weeklyTab = 0;
  }
  tweet(){
    var twitterurl = "https://twitter.com/intent/tweet?text=The current temperature at " + this.cityValue + " is " +
                     this.weatherData.currently.temperature + "\u00B0"  + 'F. ';
    twitterurl += "The weather conditions are " + this.weatherData.currently.summary + '.' + "&hashtags=CSCI571WeatherSearch";
    window.open(twitterurl);
  }

  tabChanged(tabChangeEvent: MatTabChangeEvent): void {
    this.weeklyTab = 0;
    console.log('tabChangeEvent => ', tabChangeEvent);
    console.log('index => ', tabChangeEvent.index);
    
    if (tabChangeEvent.index == 2){
      this.weeklyTab = 2;
      // console.log("forecast-data=> ",this.weeklyTab);
    }
  }

  clickedFavoriteBtn() {
    this.favoriteBtn = !this.favoriteBtn;
    let favoriteData:any= {};
    if (this.favoriteBtn) {
      favoriteData.city = this.cityValue;
      favoriteData.state = this.stateValue;
      favoriteData.longitude = this.weatherData.longitude;
      favoriteData.latitude = this.weatherData.latitude;
      favoriteData.stateSeal = this.imageString.replace('"', "");
      console.log(favoriteData);
      localStorage.setItem(this.cityValue, JSON.stringify(favoriteData));
    } else {
      console.log(favoriteData);
      localStorage.removeItem(this.cityValue);
    }
   }

}
