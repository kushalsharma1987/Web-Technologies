import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SearchengineService {


  constructor(private _http: HttpClient) { }

  public searchEngine(state: string): any{
    // this._url = this._url + address;
    var state = state;
    var search_engine_id = "006875571475360367151:dinqdswyirz";
    var search_apikey = "AIzaSyAlr1MpY6Iesx32PhwQTeFZzLMTBa3REl0";
    var _url = "https://www.googleapis.com/customsearch/v1?q=" + state + "%20State%20Seal&cx=" + search_engine_id + 
            "&imgSize=huge&imgType=news&num=1&searchType=image&key=" + search_apikey;
    return this._http.get<any>(_url);
  }
}
