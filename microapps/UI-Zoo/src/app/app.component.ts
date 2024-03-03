import {Component, OnInit} from '@angular/core';
import {Router, RouterLink, RouterOutlet} from '@angular/router';
import {MatRadioChange, MatRadioModule} from '@angular/material/radio';
import {ServiceService} from "./services/service.service";
import {MatToolbar} from "@angular/material/toolbar";
import {MatGridList, MatGridTile} from "@angular/material/grid-list";
import {Metadata} from "./services/entities";
import {MatButtonModule} from '@angular/material/button';
import {ToursComponent} from "./tour-component/tours/tours.component";
import {Location, NgIf} from '@angular/common';
import {MatIcon} from "@angular/material/icon";


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MatRadioModule, MatToolbar, MatGridList, MatGridTile, MatButtonModule, ToursComponent, MatIcon, NgIf, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  test: string = "test1";

  constructor(private service: ServiceService) {
  }
  title = 'UI-Zoo';

  toggleClick() {
    this.service.changeModus();
  }

}
