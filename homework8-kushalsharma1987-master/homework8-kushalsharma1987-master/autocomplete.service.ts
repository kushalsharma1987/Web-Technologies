import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AutocompleteService {

  // _url = 'http://localhost:3000/autocomplete/';
  _url = 'http://kushals-forecast.us-west-2.elasticbeanstalk.com/autocomplete/';
  constructor(private _http: HttpClient) { }

  public autocomplete(cityString): any{
    // this._url = this._url + address;
    // var addressUrl = street + "," + city + "," + state;
    return this._http.get<any[]>(this._url + cityString);
  }
}
