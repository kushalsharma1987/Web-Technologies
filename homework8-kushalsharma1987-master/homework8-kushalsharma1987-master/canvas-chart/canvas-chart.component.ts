import { Component, OnInit, Input, OnChanges } from '@angular/core';
import * as CanvasJS from './canvasjs.min';
import { ModalforecastService } from '../modalforecast.service';


@Component({
  selector: 'canvas-chart',
  templateUrl: './canvas-chart.component.html',
  styleUrls: ['./canvas-chart.component.css']
})
export class CanvasChartComponent implements OnInit {

  @Input() weeklyData;
  @Input() weeklyTab;
  
  constructor(private _modalforecast: ModalforecastService) { }

  
  ngOnChange() {

  }
  ngOnInit(){
    console.log(this.weeklyData);
    console.log(this.weeklyTab);
    console.log(document.getElementById('chartContainer'));
    if (this.weeklyTab == 2){
    this.makeChart();
    }
    
  }
    
  makeChart(){
    var chart = new CanvasJS.Chart('chartContainer', {
      display: true,
      animationEnabled: true,
      title: {
        text: 'Weekly Weather'
      },
      dataPointWidth: 20,
      axisX: {
        title: 'Days'
      },
      axisY: {
        includeZero: false,
        title: 'Temperature in Fahrenheit',
        interval: 10
      },
      data: [{
        type: 'rangeBar',
        showInLegend: true,
        legendText: 'Day wise temperature range',
        yValueFormatString: '',
        indexLabel: '{y[#index]}',
        toolTipContent: '<b>{label}</b>: {y[0]} to {y[1]}',
        dataPoints: this.weeklyData,
        color: '#87ceeb',
        // click:  (e) => {
        //   let date = e.dataPoint.label;
        //   let day_month_year = date.split('/');
        //   let dateObj = new Date(day_month_year[2], day_month_year[1], day_month_year[0]);
        //   let time = dateObj.getTime() / 1000;
        //   this.http.get(this.serverURL + '/' + this.latitude + '/' + this.longitude + '/' + String(time))
        //     .subscribe(data => {
        //       // @ts-ignore
        //       this.displayTemp = data.currently.temperature;
        //       this.displayCity = sessionStorage.city;
        //       this.displayDate = date;
        //       // @ts-ignore
        //       this.displayHumidity = data.currently.humidity;
        //       // @ts-ignore
        //       this.displayPrecipitation = data.currently.precipIntensity;
        //       // @ts-ignore
        //       this.displayPrecipProbability = data.currently.precipProbability;
        //       // @ts-ignore
        //       this.displaySummary = data.currently.summary;
        //       // @ts-ignore
        //       this.displayVisibility = data.currently.visibility;
        //       // @ts-ignore
        //       this.displayWindSpeed = data.currently.windSpeed;
        //       // @ts-ignore
        //       this.displayIcon = data.currently.icon;
        //       if (this.displayIcon == 'clear-day' || this.displayIcon == 'clear-night') {
        //         this.displayIcon = 'https://cdn3.iconfinder.com/data/icons/weather-344/142/sun-512.png';
        //       } else if (this.displayIcon == 'rain') {
        //         this.displayIcon = 'https://cdn3.iconfinder.com/data/icons/weather-344/142/rain-512.png';
        //       } else if (this.displayIcon == 'snow') {
        //         this.displayIcon = 'https://cdn3.iconfinder.com/data/icons/weather-344/142/snow-512.png';
        //       } else if (this.displayIcon == 'sleet') {
        //         this.displayIcon = 'https://cdn3.iconfinder.com/data/icons/weather-344/142/lightning-512.png';
        //       } else if (this.displayIcon == 'wind') {
        //         this.displayIcon = 'https://cdn4.iconfinder.com/data/icons/the-weather-is-nice-today/64/weather_10-512.png';
        //       } else if (this.displayIcon == 'fog') {
        //         this.displayIcon = 'https://cdn3.iconfinder.com/data/icons/weather-344/142/cloudy-512.png';
        //       } else if (this.displayIcon == 'cloudy') {
        //         this.displayIcon = 'https://cdn3.iconfinder.com/data/icons/weather-344/142/cloud-512.png';
        //       } else if (this.displayIcon == 'partly-cloudy-day' || this.displayIcon == 'partly-cloudy-night') {
        //         this.displayIcon = 'https://cdn3.iconfinder.com/data/icons/weather-344/142/sunny-512.png';
        //       }

        //       this.myModal.nativeElement.click();
        //     });
        // }
      }]
    });
      
    setTimeout(() => {
      chart.render();
    }, 200);
  }
  }

