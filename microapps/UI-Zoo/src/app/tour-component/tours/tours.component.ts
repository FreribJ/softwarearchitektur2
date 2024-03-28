import {Component, OnInit} from '@angular/core';
import {Router, RouterLink, RouterOutlet} from '@angular/router';
import {MatGridList, MatGridTile} from "@angular/material/grid-list";
import {ServiceService} from "../../services/service.service";
import {NgFor, NgIf} from "@angular/common";
import {Attraction, Tour} from "../../services/entities";
import {MatChip, MatChipListbox, MatChipOption} from "@angular/material/chips";
import {Subscription} from "rxjs";
import {MatIcon} from "@angular/material/icon";
import {TourOverviewComponent} from "../tour-overview/tour-overview.component";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {MatButton} from "@angular/material/button";

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
        MatProgressSpinner,
        MatButton
    ],
  templateUrl: './tours.component.html',
  styleUrl: './tours.component.scss'
})
export class ToursComponent implements OnInit {
  private modusSubscription!: Subscription;
  direktorModus = false;

  attractionListe: String[] = [];
  selectedChips: String[] = [];
  showList: Tour[] = [];
   tours?: Tour[];
  error= false;


  constructor(private service: ServiceService, private router: Router) {
    this.service.getTours().subscribe(data => {
        this.tours = data;
        this.showList = this.tours;
        console.log(this.tours);
        this.fillListe(this.tours);

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
  fillListe(tours: Tour[]) {
    for (let tour of tours) {
      if (tour.attractions.length != 0) {
        for (let attraction of tour.attractions) {
          if (!this.attractionListe.includes(attraction.attraction)) {
            this.attractionListe.push(attraction.attraction);
          }
        }
      }
    }
  }
  toggleSelection(chip: String): void {
    if (this.isSelected(chip)) {
      this.selectedChips = this.selectedChips.filter(selectedChip => selectedChip !== chip);
    } else {
      this.selectedChips.push(chip);
    }
    console.log(this.selectedChips);

  }
  isSelected(chip: String): boolean {
    return this.selectedChips.includes(chip);
  }

  filtern() {
    if(this.tours){
      if (this.selectedChips.length == 0) {
        this.showList = this.tours;
        return;
      } else { this.showList=this.tours.filter(tour => {
       let attractionNamen= tour.attractions.map(attraction=> attraction.attraction)
        for (let attraction of this.selectedChips) {
          if (!attractionNamen.includes(attraction)) {
            return false;
          }
        }
        return true;
      });
      }
    }
  }
  loeschen()
  {
    if(this.tours)
      this.showList = this.tours;
    this.selectedChips = [];
  }

  add() {

  }
}
