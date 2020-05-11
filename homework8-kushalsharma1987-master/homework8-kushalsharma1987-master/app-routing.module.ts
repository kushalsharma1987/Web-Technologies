import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ForecastDataComponent } from './forecast-data/forecast-data.component';
import { ForecastFormComponent } from './forecast-form/forecast-form.component';
import { CurrentComponent } from './current/current.component';
import { HourlyComponent } from './hourly/hourly.component';
import { WeeklyComponent } from './weekly/weekly.component';

const routes: Routes = [
  {path:  '#nav-current', component: CurrentComponent},
  {path:  '#nav-hourly', component: HourlyComponent},
  {path:  '#nav-weekly', component: WeeklyComponent}
  // {path: 'forecast-form', component: ForecastFormComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
