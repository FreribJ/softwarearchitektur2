import {Component, OnInit} from '@angular/core';
import {Router, RouterLink, RouterOutlet} from '@angular/router';
import {MatGridList, MatGridTile} from "@angular/material/grid-list";
import {ServiceService} from "../../services/service.service";
import {NgFor, NgIf} from "@angular/common";
import {Tour} from "../../services/entities";
import {MatChip, MatChipListbox, MatChipOption} from "@angular/material/chips";
import {Subscription} from "rxjs";
import {MatIcon} from "@angular/material/icon";
import {TourOverviewComponent} from "../tour-overview/tour-overview.component";
import {MatProgressSpinner} from "@angular/material/progress-spinner";

@Component({

  selector: 'app-tours',
  standalone: true,
  imports: [
    MatGridTile,
    MatGridList,
    NgFor,
    MatChip,
    MatChipOption,
    RouterLink,
    MatChipListbox,
    NgIf,
    MatIcon,
    TourOverviewComponent,
    MatProgressSpinner
  ],
  templateUrl: './tours.component.html',
  styleUrl: './tours.component.scss'
})
export class ToursComponent implements OnInit {
  private modusSubscription!: Subscription;
  direktorModus = false;

  protected tours?: Tour[] ;
  error= false;


  constructor(private service: ServiceService, private router: Router) {
    this.service.getTours().subscribe(data => {
        this.tours = data;
        console.log(this.tours);
      },error => {
        this.error = true;
        console.log("Error: " + error);
      }
    );
  }

  ngOnInit() {
    this.modusSubscription! = this.service.modus$.subscribe(enabled => {
      this.direktorModus = enabled;
    });
  }
}
