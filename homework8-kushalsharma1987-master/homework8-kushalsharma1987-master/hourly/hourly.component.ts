import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'hourly',
  templateUrl: './hourly.component.html',
  styleUrls: ['./hourly.component.css']
})
export class HourlyComponent implements OnInit {

  @Input() weatherData;
  temperatureData = [];
  pressureData = [];
  humidityData = [];
  ozoneData = [];
  visibilityData = [];
  windSpeedData = [];
  labelData = [];
  constructor() { }

  ngOnInit() {
    var hourlyList = this.weatherData.hourly.data;
    for(var i=0; i < 24; i++){
      this.temperatureData.push(Math.round(hourlyList[i].temperature));
      this.pressureData.push(Math.round(hourlyList[i].pressure));
      this.humidityData.push(Math.round(hourlyList[i].humidity * 100));
      this.ozoneData.push(Math.round(hourlyList[i].ozone));
      this.visibilityData.push(Math.round(hourlyList[i].visibility));
      this.windSpeedData.push(Math.round(hourlyList[i].windSpeed));
      this.labelData.push(i);
    }
    // var dropdown = <HTMLInputElement> document.getElementById("hourlyChartOptions");
    // dropdown.value = "temperature";
    this.onChangeBarChart("temperature");
  }


  
  chartData: any=[];
  maxValue: any=[];
  public barChartOptions = {
    scaleShowVerticalLines : false,
    responsive: true,
    layout: {padding : { top: 2, bottom: 2}},
    scales: {
              xAxes: [{display: true,scaleLabel: {display: true,labelString: 'Time difference from current hour'}}],
              yAxes: [{display:true, scaleLabel: { display:true, labelString: 'Fahrenheit'}, ticks: { 
              //   callback : function(value,index,values){
              //     this.maxValue = values;
              //     return value;
              // }, max: this.maxValue[0] + 2,
              beginAtZero: true}}]
            }
  };
  public barChartLabels = this.labelData;
  public barChartType = 'bar';
  public barChartLegend = true;
  public barChartData = [];
  // public barChartData =  [
  //   {data: [65, 59, 80, 81, 56, 55, 40], label: 'Series A'},
  //   {data: [28, 48, 40, 19, 86, 27, 90], label: 'Series B'}
  // ];

  onChangeBarChart(Legend){
    // console.log("Selected Parameter:", Legend);
    switch(Legend){
      case "temperature":{
        this.barChartData = [{data: this.temperatureData, label: 'temperature', backgroundColor: '#87ceeb', hoverBackgroundColor: '#4682b4'}];
        this.barChartOptions =    { 
          scaleShowVerticalLines : false,
          responsive: true,
          layout: {padding : { top: 2, bottom: 2}},
          scales: {
          xAxes: [{display: true,scaleLabel: {display: true,labelString: 'Time difference from current hour'}}],
          yAxes: [{display:true, scaleLabel: { display:true, labelString: 'Fahrenheit'}, ticks: {                
        //     callback : function(value,index,values){
        //     this.maxValue = values;
        //     return value;
        // }, max: this.maxValue[0] + 2,
        beginAtZero: true}}]
        }};
        break;
      }
      case 'pressure':{
        this.barChartData = [{data: this.pressureData, label: 'pressure', backgroundColor: '#87ceeb', hoverBackgroundColor: '#4682b4'}];
        this.barChartOptions =    { 
          scaleShowVerticalLines : false,
          responsive: true,
          layout: {padding : { top: 2, bottom: 2}},
          scales: {
          xAxes: [{display: true,scaleLabel: {display: true,labelString: 'Time difference from current hour'}}],
          yAxes: [{display:true, scaleLabel: { display:true, labelString: 'Millibars'}, ticks: {                
        //     callback : function(value,index,values){
        //     this.maxValue = values;
        //     return value;
        // }, max: this.maxValue[0] + 2,
        beginAtZero: true}}]
        }};
        break;
      }
      case 'humidity':{
        this.barChartData = [{data: this.humidityData, label: 'humidity', backgroundColor: '#87ceeb', hoverBackgroundColor: '#4682b4'}];
        // this.barChartOptions.scales.yAxes[ 2 ].scaleLabel.labelString = "% Humidity";
        this.barChartOptions =    { 
          scaleShowVerticalLines : false,
          responsive: true,
          layout: {padding : { top: 2, bottom: 2}},
          scales: {
          xAxes: [{display: true,scaleLabel: {display: true,labelString: 'Time difference from current hour'}}],
          yAxes: [{display:true, scaleLabel: { display:true, labelString: '% Humidity'}, ticks: {
          //   callback : function(value,index,values){
          //     this.maxValue = values;
          //     return value;
          // }, max: this.maxValue[0] + 2,
          beginAtZero: true}}]
        }};
        break;
      }
      case 'ozone':{
        this.barChartData = [{data: this.ozoneData, label: 'ozone', backgroundColor: '#87ceeb', hoverBackgroundColor: '#4682b4'}];
        // this.barChartOptions.scales.yAxes[ 3 ].scaleLabel.labelString = "Dobson Units";
        this.barChartOptions =    { 
          scaleShowVerticalLines : false,
          responsive: true,
          layout: {padding : { top: 2, bottom: 2}},
          scales: {
          xAxes: [{display: true,scaleLabel: {display: true,labelString: 'Time difference from current hour'}}],
          yAxes: [{display:true, scaleLabel: { display:true, labelString: 'Dobson Units'}, ticks: {
          //   callback : function(value,index,values){
          //     this.maxValue = values;
          //     return value;
          // }, max: this.maxValue[0] + 2,
          beginAtZero: true}}]
        }};
        break;
      }
      case 'visibility':{
        this.barChartData = [{data: this.visibilityData, label: 'visibility', backgroundColor: '#87ceeb', hoverBackgroundColor: '#4682b4'}];
        // this.barChartOptions.scales.yAxes[ 4 ].scaleLabel.labelString = "Miles (Maximum 10)";
        this.barChartOptions =    { 
          scaleShowVerticalLines : false,
          responsive: true,
          layout: {padding : { top: 2, bottom: 2}},
          scales: {
          xAxes: [{display: true,scaleLabel: {display: true,labelString: 'Time difference from current hour'}}],
          yAxes: [{display:true, scaleLabel: { display:true, labelString: 'Miles (Maximum 10)'}, ticks: {
          //   callback : function(value,index,values){
          //     this.maxValue = values;
          //     return value;
          // }, max: this.maxValue[0] + 2,
          beginAtZero: true}}]
        }};
        break;
      }
      case 'windSpeed':{
        this.barChartData = [{data: this.temperatureData, label: 'windSpeed', backgroundColor: '#87ceeb', hoverBackgroundColor: '#4682b4'}];
        // this.barChartOptions.scales.yAxes[ 5 ].scaleLabel.labelString = "Miles per Hour";
        this.barChartOptions =    { 
          scaleShowVerticalLines : false,
          responsive: true,
          layout: {padding : { top: 2, bottom: 2}},
          scales: {
          xAxes: [{display: true,scaleLabel: {display: true,labelString: 'Time difference from current hour'}}],
          yAxes: [{display:true, scaleLabel: { display:true, labelString: 'Miles per Hour'}, ticks: {
          //   callback : function(value,index,values){
          //     this.maxValue = values;
          //     return value;
          // }, max: this.maxValue[0] + 2,
          beginAtZero: true}}]
        }};
        break;
      }
      default:{
        this.barChartData = [{data: this.temperatureData, label: 'temperature', backgroundColor: '#87ceeb', hoverBackgroundColor: '#4682b4'}];
        // this.barChartOptions.scales.yAxes[ 6 ].scaleLabel.labelString = "Fahrenheit";
        this.barChartOptions =    { 
          scaleShowVerticalLines : false,
          responsive: true,
          layout: {padding : { top: 2, bottom: 2}},
          scales: {
          xAxes: [{display: true,scaleLabel: {display: true,labelString: 'Time difference from current hour'}}],
          yAxes: [{display:true, scaleLabel: { display:true, labelString: 'Fahrenheit'}, ticks: {
          //   callback : function(value,index,values){
          //     this.maxValue = values;
          //     return value;
          // }, max: this.maxValue[0] + 2,
          beginAtZero: true}}]
        }};
        break;
      }
    }

  }


}
