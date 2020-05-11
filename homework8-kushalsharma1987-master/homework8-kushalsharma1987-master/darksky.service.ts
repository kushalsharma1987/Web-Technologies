import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DarkskyService {

  // _url = 'http://localhost:3000/forecast/';
  _url = 'http://kushals-forecast.us-west-2.elasticbeanstalk.com/forecast/';
  constructor(private _http: HttpClient) { }

  public forecast(lat,lon){
    // this._url = this._url + address;
    var latlonUrl = lat + "," + lon;
    return this._http.get<any>(this._url + latlonUrl);
  }
}
