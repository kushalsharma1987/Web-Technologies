import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class IpapiService {

  _url = 'http://ip-api.com/json/?fields=status,message,country,countryCode,region,regionName,city,zip,lat,lon,timezone,isp,org,as,query';
  constructor(private _http: HttpClient) { }

  public currentLocation(){
    // this._url = this._url + address;
    // var latlonUrl = lat + "," + lon;
    return this._http.get<any>(this._url);
  }
}
