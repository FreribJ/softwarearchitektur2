import {Component, OnInit} from '@angular/core';
import {MatChip, MatChipListbox, MatChipOption} from "@angular/material/chips";
import {MatIcon} from "@angular/material/icon";
import {NgForOf, NgIf} from "@angular/common";
import {ServiceService} from "../../services/service.service";
import {Subscription} from "rxjs";
import {Attraction} from "../../services/entities";
import {Router, RouterLink} from "@angular/router";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {AttractionOverviewComponent} from "../attraction-overview/attraction-overview.component";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-attractions',
  standalone: true,
  imports: [
    MatChipListbox,
    MatChipOption,
    MatIcon,
    NgForOf,
    NgIf,
    RouterLink,
    MatChip,
    MatProgressSpinner,
    AttractionOverviewComponent,
    MatButton
  ],
  templateUrl: './attractions.component.html',
  styleUrl: './attractions.component.scss'
})
export class AttractionsComponent implements OnInit {
  private modusSubscription!: Subscription;
  direktorModus = false;
  attractions: Attraction[] = [];
  error = false;
  tagListe: String[] = [];
  selectedChips: String[] = [];
  showList: Attraction[] = [];

  constructor(private service: ServiceService, private router: Router) {
    this.service.getAttractions().subscribe(data => {
        this.attractions = data;
        this.showList = this.attractions;
        console.log(this.attractions);
        this.fillListe(this.attractions);
      },
      error => {
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

  fillListe(attractions: Attraction[]) {
    for (let attraction of attractions) {
      if (attraction.tags.length != 0) {
        for (let tag of attraction.tags) {
          if (!this.tagListe.includes(tag)) {
            this.tagListe.push(tag);
          }
        }
      }
    }
  }

  detailTour(name: String) {
    this.router.navigate(['tour-overview', name]);
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
    if (this.selectedChips.length == 0) {
      this.showList = this.attractions;
      return;
    } else { this.showList=this.attractions.filter(attraction => {
      for (let tag of this.selectedChips) {
        if (!attraction.tags.includes(tag)) {
          return false;
        }
      }
      return true;
    });
  }
  }
    loeschen()
    {
      this.showList = this.attractions;
      this.selectedChips = [];
    }

  add() {

  }
}
