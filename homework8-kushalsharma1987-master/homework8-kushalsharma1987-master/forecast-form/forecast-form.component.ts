import { Component, OnInit } from '@angular/core';
import { GeocodeService } from '../geocode.service';
import { Address } from '../address';
import { DarkskyService } from '../darksky.service';
import { IpapiService } from '../ipapi.service';
import { SearchengineService } from '../searchengine.service';
import { AutocompleteService} from '../autocomplete.service';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import { Validators } from '@angular/forms';
import { CombineLatestOperator } from 'rxjs/internal/observable/combineLatest';


@Component({
  selector: 'forecast-form',
  templateUrl: './forecast-form.component.html',
  styleUrls: ['./forecast-form.component.css']
})
export class ForecastFormComponent implements OnInit {
  addressForm = this.fb.group({
    street: ['', Validators.required],
    city: ['', Validators.required],
    state: ['', Validators.required],
  })

  get street(){
    return this.addressForm.get('street');
  }
  get city(){
    return this.addressForm.get('city');
  }
  get state(){
    return this.addressForm.get('state');
  }
  // addressModel = new Address('','','');
  
  constructor(private fb: FormBuilder, private _geocodeService: GeocodeService, private _darkskyservice: DarkskyService,
    private _ipapiService: IpapiService, private _searchEngineService: SearchengineService, private _autocompleteService: AutocompleteService){}
  
    ngOnInit(): void {
      // this.city.valueChanges.subscribe(
      //   data=>{}
      // )
      
    }
  // public geocodeData = [];
  // public forecastData = [];
  // public lat;
  // public long;
  public geocodeData : {};
  public forecastData = {};
  public forecastDaily = {};
  public currlocData;
  autocompleteData;
  public autocompleteOptions =  [];
  public weatherString : string;
  public imageString : string;
  public cityValue: string;
  stateValue: string;
  public statevalue : string;

  public lat : string;
  public long : string;
  currentLocCtrl = false;
  progressBar = false;
  invalidAddress = false;
  results = false;
  favorites = false;


  JSONstates = '[	{	"Abbreviation":"AL",	"State":"Alabama"	},	{	"Abbreviation":"AK",	"State":"Alaska"	},	{	"Abbreviation":"AZ",	"State":"Arizona"	},	{	"Abbreviation":"AR",	"State":"Arkansas"	},	{	"Abbreviation":"CA",	"State":"California"	},	{	"Abbreviation":"CO",	"State":"Colorado"	},	{	"Abbreviation":"CT",	"State":"Connecticut"	},	{	"Abbreviation":"DE",	"State":"Delaware"	},	{	"Abbreviation":"DC",	"State":"District Of Columbia"	},	{	"Abbreviation":"FL",	"State":"Florida"	},	{	"Abbreviation":"GA",	"State":"Georgia"	},	{	"Abbreviation":"HI",	"State":"Hawaii"	},	{	"Abbreviation":"ID",	"State":"Idaho"	},	{	"Abbreviation":"IL",	"State":"Illinois"	},	{	"Abbreviation":"IN",	"State":"Indiana"	},	{	"Abbreviation":"IA",	"State":"Iowa"	},	{	"Abbreviation":"KS",	"State":"Kansas"	},	{	"Abbreviation":"KY",	"State":"Kentucky"	},	{	"Abbreviation":"LA",	"State":"Louisiana"	},	{	"Abbreviation":"ME",	"State":"Maine"	},	{	"Abbreviation":"MD",	"State":"Maryland"	},	{	"Abbreviation":"MA",	"State":"Massachusetts"	},	{	"Abbreviation":"MI",	"State":"Michigan"	},	{	"Abbreviation":"MN",	"State":"Minnesota"	},{	"Abbreviation":"MS",	"State":"Mississippi"	},{	"Abbreviation":"MO",	"State":"Missouri"	},{	"Abbreviation":"MT",	"State":"Montana"	},{	"Abbreviation":"NE",	"State":"Nebraska"	},{	"Abbreviation":"NV",	"State":"Nevada"	},{	"Abbreviation":"NH",	"State":"New Hampshire"	},{	"Abbreviation":"NJ",	"State":"New Jersey"	},{	"Abbreviation":"NM",	"State":"New Mexico"	},{	"Abbreviation":"NY",	"State":"New York"	},{	"Abbreviation":"NC",	"State":"North Carolina"	},{	"Abbreviation":"ND",	"State":"North Dakota"	},{	"Abbreviation":"OH",	"State":"Ohio"	},{	"Abbreviation":"OK",	"State":"Oklahoma"	},{	"Abbreviation":"OR",	"State":"Oregon"	},{	"Abbreviation":"PA",	"State":"Pennsylvania"	},{	"Abbreviation":"RI",	"State":"Rhode Island"	},{	"Abbreviation":"SC",	"State":"South Carolina"	},{	"Abbreviation":"SD",	"State":"South Dakota"	},{	"Abbreviation":"TN",	"State":"Tennessee"	},{	"Abbreviation":"TX",	"State":"Texas"	},{	"Abbreviation":"UT",	"State":"Utah"	},{	"Abbreviation":"VT",	"State":"Vermont"	},{	"Abbreviation":"VA",	"State":"Virginia"	},{	"Abbreviation":"WA",	"State":"Washington"	},{	"Abbreviation":"WV",	"State":"West Virginia"	},{	"Abbreviation":"WI",	"State":"Wisconsin"	},	{	"Abbreviation":"WY",	"State":"Wyoming"	}	]';
  states = JSON.parse(this.JSONstates);
  // stateError = true;

  // validateState(value: string){
  //   if (value == "default" || value == ""){
  //     this.stateError = true;
  //   }else{
  //     this.stateError = false;
  //   }
  // }

  
  clickedClear(){
    // var street = <HTMLInputElement> document.getElementById("street");
    // var city = <HTMLInputElement> document.getElementById("city");
    // var state = <HTMLInputElement> document.getElementById("state");
    this.invalidAddress = false;
    this.addressForm.get('street').enable();
    this.addressForm.get('city').enable();
    this.addressForm.get('state').enable();
    // street.disabled = false;
    // city.disabled = false;
    // state.disabled = false;
    this.currentLocCtrl = false;
    this.geocodeData = {};
    this.weatherString = '';
    this.forecastDaily = {};
    this.forecastData = {};
    this.progressBar = false;
    this.results = true;
    this.favorites = false;
  }
  clickedCurrentLoc(){
    this.progressBar = false;
    this.invalidAddress = false;
    var checkbox = <HTMLInputElement> document.getElementById("currentLoc");
    var street = <HTMLInputElement> document.getElementById("street");
    var city = <HTMLInputElement> document.getElementById("city");
    var state = <HTMLInputElement> document.getElementById("state");
    if (checkbox.checked){
      street.value = '';
      city.value = '';
      state.value = '';
      this.addressForm.get('street').disable();
      this.addressForm.get('city').disable();
      this.addressForm.get('state').disable();
      this.currentLocCtrl = true;
      this._ipapiService.currentLocation()
      .subscribe(data => {this.currlocData = data, this.lat = data.lat, this.long = data.lon, console.log(this.currlocData)},
        error => console.error('Error!', error))

    }else {
      street.disabled = false;
      city.disabled = false;
      state.disabled = false;
      this.currentLocCtrl = false;
    }
  }

  cityAutocomplete(){
    
    this.autocompleteOptions = [];
    if (this.addressForm.get('city').value.length > 0){
    this._autocompleteService.autocomplete(this.addressForm.get('city').value)
      .subscribe(
        data => { this.autocompleteData = data;
              // console.log(data);
                  var i = 0;
                  while(i < this.autocompleteData.predictions.length || i < 6){
                      this.autocompleteOptions.push(this.autocompleteData.predictions[i].structured_formatting.main_text);
                      i++;
                  }
                },
        error => console.error('Error!', error))
    }
  }

  onSubmit(){
    this.progressBar = true;
    this.results = true;
    this.favorites = false;
    if (this.currentLocCtrl) {
      this.cityValue = this.currlocData.city;
      this.stateValue = this.currlocData.regionName;
      console.log(this.currlocData);
      console.log(this.cityValue);
      this._darkskyservice.forecast(this.lat,this.long)
          .subscribe(data => {this.forecastData = data, 
            this.forecastDaily = data.daily.data, this.weatherString = JSON.stringify(this.forecastData), console.log(this.forecastData)},
            error => console.error('Error!', error))
        this._searchEngineService.searchEngine(this.currlocData.regionName)
        .subscribe(
          data => {this.imageString = data.items[0].link, this.progressBar = false, console.log(this.imageString)},
          error => console.error('Error!', error))      
      
    }else {
      console.log(this.street.value,this.city.value, this.state.value)
      this.cityValue = this.city.value;
      this.stateValue = this.state.value;
    this._geocodeService.geocode(this.street.value,this.city.value,this.state.value)
      .subscribe(
        data => {this.geocodeData=(data.results); 
          this.lat=(data.results[0].geometry.location.lat); this.long=(data.results[0].geometry.location.lng);
          console.log(this.geocodeData); this.cityValue = data.results[0].address_components[1].long_name;
          if (Object.keys(data).length == 0) {
            this.progressBar = false;
            this.invalidAddress = true;
          } else{
          this._darkskyservice.forecast(this.lat,this.long)
          .subscribe(data => {this.forecastData = data,this.progressBar = false,
            this.forecastDaily = data.daily.data, this.weatherString = JSON.stringify(this.forecastData), console.log(this.forecastData)},
            error => console.error('Error!', error))}},

        error => console.error('Error!', error))
    this._searchEngineService.searchEngine(this.state.value)
      .subscribe(
        data => {this.imageString = data.items[0].link, console.log(this.imageString)},
        error => console.error('Error!', error))     
    }
    
  }

clickedResults(){
  this.results = true;
  this.favorites = false;
  this.invalidAddress = true;
  var resultBtn = <HTMLInputElement> document.getElementById("results");
  var favoritesBtn = <HTMLInputElement> document.getElementById("favorites");
  resultBtn.style.backgroundColor = 'steelblue';
  resultBtn.style.color = 'white';
  favoritesBtn.style.backgroundColor = 'white';
  favoritesBtn.style.color = 'grey';

}
clickedFavorites(){
  this.results = false;
  this.favorites = true;
  this.invalidAddress = false;
  var resultBtn = <HTMLInputElement> document.getElementById("results");
  var favoritesBtn = <HTMLInputElement> document.getElementById("favorites");
  favoritesBtn.style.backgroundColor = 'steelblue';
  favoritesBtn.style.color = 'white';
  resultBtn.style.backgroundColor = 'white';
  resultBtn.style.color = 'grey';
}

}
