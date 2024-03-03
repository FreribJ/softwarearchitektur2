import { Component } from '@angular/core';
import {ServiceService} from "../services/service.service";
import {Metadata} from "../services/entities";
import {MatGridList, MatGridTile} from "@angular/material/grid-list";
import {NgFor, NgIf} from "@angular/common";
import {MatIcon} from "@angular/material/icon";
import {Subscription} from "rxjs";
import {RouterLink} from "@angular/router";
import {MatProgressSpinner} from "@angular/material/progress-spinner";

@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [
    MatGridTile,
    MatGridList,
    NgFor,
    NgIf,
    MatIcon,
    RouterLink,
    MatProgressSpinner
  ],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.scss'
})
export class HomepageComponent {
  private modusSubscription!: Subscription;
  direktorModus = false;
   data?: Metadata;
    error = false;
constructor(private service: ServiceService) {
  this.service.getMetadata().subscribe(data => {
    this.data= data;
  console.log(this.data?.prices);},
    error => {
      this.error = true;
      console.log("Error: " + error);
    })
}
  ngOnInit() {
    this.modusSubscription! = this.service.modus$.subscribe(enabled => {
      this.direktorModus = enabled;
    });
  }
}
