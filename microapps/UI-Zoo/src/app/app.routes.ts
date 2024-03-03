import { Routes } from '@angular/router';
import {ToursComponent} from "./tour-component/tours/tours.component";
import {AttractionsComponent} from "./attraction-component/attractions/attractions.component";
import {AppComponent} from "./app.component";
import {HomepageComponent} from "./homepage/homepage.component";
import {TourOverviewComponent} from "./tour-component/tour-overview/tour-overview.component";
import {AttractionOverviewComponent} from "./attraction-component/attraction-overview/attraction-overview.component";
import {EditMetadataComponent} from "./homepage/edit-metadata/edit-metadata.component";

export const routes: Routes = [
  {path: 'tours', component: ToursComponent},
  {path: '', component: HomepageComponent},
  {path: 'attractions', component: AttractionsComponent},
  {path: 'attraction-overview/:id', component: AttractionOverviewComponent},
  {path: 'tour-overview/:id', component: TourOverviewComponent},
  {path: 'editMetadata', component: EditMetadataComponent},
];
