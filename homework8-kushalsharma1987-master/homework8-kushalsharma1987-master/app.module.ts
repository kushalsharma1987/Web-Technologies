import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ForecastFormComponent } from './forecast-form/forecast-form.component';
import { WelcomePageComponent } from './welcome-page/welcome-page.component';
import { ForecastDataComponent } from './forecast-data/forecast-data.component';
import { CurrentComponent } from './current/current.component';
import { HourlyComponent } from './hourly/hourly.component';
import { WeeklyComponent } from './weekly/weekly.component';
import { TabGroupBasicExampleComponent } from './tab-group-basic-example/tab-group-basic-example.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTabsModule } from '@angular/material';
import { ChartsModule } from 'ng2-charts';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatFormFieldModule, MatInputModule} from '@angular/material';
import { ProgressBarComponent } from './progress-bar/progress-bar.component';
import { CanvasChartComponent } from './canvas-chart/canvas-chart.component';
import { FavoritesComponent } from './favorites/favorites.component';

@NgModule({
  declarations: [
    AppComponent,
    ForecastFormComponent,
    WelcomePageComponent,
    ForecastDataComponent,
    CurrentComponent,
    HourlyComponent,
    WeeklyComponent,
    TabGroupBasicExampleComponent,
    ProgressBarComponent,
    CanvasChartComponent,
    FavoritesComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatTabsModule,
    ChartsModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
