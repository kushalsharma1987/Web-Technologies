import { Component, OnInit, Input } from '@angular/core';
import { StringifyOptions } from 'querystring';


@Component({
  selector: 'current',
  templateUrl: './current.component.html',
  styleUrls: ['./current.component.css']
})
export class CurrentComponent implements OnInit {
  
  @Input() weatherData : any;
  @Input() geocodeData : any;
  @Input() currlocData : any;
  @Input() imageString: string;
  @Input() cityValue: string;
  temp:number;
  // state = <HTMLInputElement> document.getElementById("state");
  // constructor(private _searchEngineService: SearchengineService)) { }
  constructor(){}
  ngOnInit() {
    this.temp = Math.round(this.weatherData.currently.temperature);
    // this.getState();
    
  }

  // getState():void{
  //   this.
  // }
}
