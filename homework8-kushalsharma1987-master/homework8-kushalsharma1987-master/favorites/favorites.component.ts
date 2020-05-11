import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {

  favorites = [];
  hasRecords;
  @Output() cityClicked = new EventEmitter();
  constructor() { }
  ngOnInit() {
    for (let i = 0; i < localStorage.length; i++) {
      this.favorites.push(JSON.parse(localStorage[localStorage.key(i)]));
    }
    if (localStorage.length == 0){
      this.hasRecords = false;  
    }else{
      this.hasRecords = true;
    }
    console.log(this.favorites);
  }

  deleteFavorite($event) {
    const cityKey = $event.composedPath()[3].children[2].children[0].innerHTML;

    const tableIndex = $event.composedPath()[3].children[0].children[0].innerHTML;
    this.favorites.splice(tableIndex - 1, 1);
    localStorage.removeItem(cityKey);
    // tslint:disable-next-line:triple-equals
    if (localStorage.length == 0) {
      this.hasRecords = false;
    }
  }

  getFavoriteResult($event) {
    this.cityClicked.emit($event);
  }

}
