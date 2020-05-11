import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Address } from './address';

@Injectable({
  providedIn: 'root'
})
export class GeocodeService {
  
  // _url = 'http://localhost:3000/geocode/';
  _url = 'http://kushals-forecast.us-west-2.elasticbeanstalk.com/geocode/';
  constructor(private _http: HttpClient) { }

  public geocode(street, city, state): any{
    // this._url = this._url + address;
    var addressUrl = street + "," + city + "," + state;
    return this._http.get<any[]>(this._url + addressUrl);
  }
}
