import { Routes } from '@angular/router';
import {EditTourComponent} from "./edit-tour/edit-tour.component";
import {AppComponent} from "./app.component";
import {EditAttractionComponent} from "./edit-attraction/edit-attraction.component";

export const routes: Routes = [{path: 'tourEdit', component: EditTourComponent},
  {path: 'attractionEdit', component: EditAttractionComponent},
  {path: '', component: AppComponent}];
