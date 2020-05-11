import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ModalforecastService {

  // _url = 'http://localhost:3000/forecastmodal/';
  _url = 'http://kushals-forecast.us-west-2.elasticbeanstalk.com/forecastmodal/';
  constructor(private _http: HttpClient) { }

  public forecastModal(lat,lon,time){
    // this._url = this._url + address;
    var latlonUrl = lat + "," + lon + "," + time;
    return this._http.get<any>(this._url + latlonUrl);
  }
}
