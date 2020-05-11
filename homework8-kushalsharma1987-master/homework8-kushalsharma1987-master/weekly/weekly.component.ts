import { Component, OnInit, Input,ViewChild, AfterViewInit, ElementRef } from '@angular/core';
import { ThemeService } from 'ng2-charts';
// import CanvasJS from 'canvasjs';

@Component({
  selector: 'weekly',
  templateUrl: './weekly.component.html',
  styleUrls: ['./weekly.component.css']
})
export class WeeklyComponent implements OnInit, AfterViewInit {

  // @ViewChild('anchor', {static: false}) anchor: ElementRef;
  // @ViewChild('myModal', {static: false}) myModal: ElementRef;
  @Input() weatherData;
  @Input() weeklyTab;
  showChart = false;
  lat: any;
  long: any;
  timeData = [];
  temperatureLowData = [];
  temperatureHighData = [];
  weeklyData = [];
  weeklyString: string;
  displayDate;
  displayCity;
  displayTemp;
  displaySummary;
  displayHumidity;
  displayWindspeed;
  displayPrecipitation;
  displayVisibility;
  displayPrecipProbability;
  displayWindSpeed;
  displayIcon;
  constructor() { }

    
  ngAfterViewInit() {

  }

 
  ngOnInit(){
    console.log("Weekly:", this.weeklyTab);
    this.showChart = false;
    this.weeklyString = null;
    var weeklyList = this.weatherData.daily.data;
    this.lat = this.weatherData.latitude;
    this.long = this.weatherData.longitude;
    for(var i=0; i < 8; i++){
      var unixtime = weeklyList[i].time;
      var date = new Date(0);
      date.setUTCSeconds(unixtime);
      var dateformat = date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear();
      this.weeklyData.push({x: (i + 1) * 10, y: [Math.round(weeklyList[i].temperatureLow), Math.round(weeklyList[i].temperatureHigh)],
                              label: dateformat});
      // this.timeData.push(Math.round(weeklyList[i].time));
      // this.temperatureLowData.push(Math.round(weeklyList[i].temperatureLow));
      // this.temperatureHighData.push(Math.round(weeklyList[i].temperatureHigh));
    }
    this.weeklyString = JSON.stringify(this.weeklyData);
    console.log(this.weeklyString);
    if(this.weeklyTab == 2){
      this.showChart = true;
    } else {
      this.showChart = false;
    }
    // const chart = new CanvasJS.Chart('chartContainer', {
    //   display: false,
    //   animationEnabled: true,
    //   title: {
    //     text: 'Weekly Weather'
    //   },
    //   dataPointWidth: 20,
    //   axisX: {
    //     title: 'Days'
    //   },
    //   axisY: {
    //     includeZero: false,
    //     title: 'Temperature in Fahrenheit',
    //     interval: 10
    //   },
    //   data: [{
    //     type: 'rangeBar',
    //     showInLegend: true,
    //     legendText: 'Day wise temperature range',
    //     yValueFormatString: '',
    //     indexLabel: '{y[#index]}',
    //     toolTipContent: '<b>{label}</b>: {y[0]} to {y[1]}',
    //     dataPoints: this.weeklyData,
    //     color: '#87ceeb',
    //   }]
    // });
      
    // setTimeout(() => {
    //   chart.render();
    // }, 200);
    // }
}
}

  // public barChartOptions = {
  //   scaleShowVerticalLines : false,
  //   responsive: true,
  //   layout: {padding : { top: 2, bottom: 2}},
  //   scales: {
  //             xAxes: [{display: true,scaleLabel: {display: true,labelString: 'Time difference from current hour'}}],
  //             yAxes: [{display:true, scaleLabel: { display:true, labelString: 'Fahrenheit'}, ticks: { 
  //             //   callback : function(value,index,values){
  //             //     this.maxValue = values;
  //             //     return value;
  //             // }, max: this.maxValue[0] + 2,
  //             beginAtZero: true}}]
  //           }
  // };
  // // public barChartLabels = this.labelData;
  // public barChartType = 'rangeBar';
  // public barChartLegend = true;
  // public barChartData = [];

  // RangeBarChart(){


  // }