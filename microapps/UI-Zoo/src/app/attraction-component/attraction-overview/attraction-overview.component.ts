import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink} from "@angular/router";
import {ServiceService} from "../../services/service.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Attraction, Tour} from "../../services/entities";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {NgForOf, NgIf} from "@angular/common";
import {MatIcon} from "@angular/material/icon";
import {MatChip, MatChipListbox} from "@angular/material/chips";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-attraction-overview',
  standalone: true,
  imports: [
    MatProgressSpinner,
    NgIf,
    MatIcon,
    MatChip,
    MatChipListbox,
    NgForOf,
    RouterLink
  ],
  templateUrl: './attraction-overview.component.html',
  styleUrl: './attraction-overview.component.scss'
})
export class AttractionOverviewComponent implements OnInit {
  @Input('attraction')
  attraction?: Attraction ;
   error= false;
  private modusSubscription!: Subscription;
  direktorModus = false;
  constructor(private route: ActivatedRoute, private service: ServiceService) {

  }

  ngOnInit() {
    this.modusSubscription! = this.service.modus$.subscribe(enabled => {
      this.direktorModus = enabled;
    });
    if (this.attraction == null) {

      this.route.paramMap.subscribe(params => {
        this.service.getAttraction(params.get('id') as string).subscribe(data => {
          this.attraction = data;
          console.log(this.attraction);
        });
      }, error => {
        this.error = true;
        console.log("Error: " + error);
      });

    }
  }
}
