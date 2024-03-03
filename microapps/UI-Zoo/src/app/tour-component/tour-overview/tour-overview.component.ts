import {Component, Input, input, OnInit} from '@angular/core';
import {ServiceService} from "../../services/service.service";
import {Tour} from "../../services/entities";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {NgForOf, NgIf} from "@angular/common";
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Subscription} from "rxjs";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {MatChip, MatChipListbox, MatChipOption} from "@angular/material/chips";
import {MatButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-tour-overview',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    ReactiveFormsModule,
    MatLabel,
    MatFormField,
    MatInput,
    MatProgressSpinner,
    MatChipListbox,
    MatChipOption,
    RouterLink,
    MatButton,
    MatIcon,
    MatChip
  ],
  templateUrl: './tour-overview.component.html',
  styleUrl: './tour-overview.component.scss'
})
export class TourOverviewComponent implements OnInit {

  @Input('tour')
  tour?: Tour;


  private modusSubscription!: Subscription;
  direktorModus = false;

  error: boolean = false;


  constructor(private route: ActivatedRoute, private service: ServiceService, private formBuilder: FormBuilder) {
    //this.tourForm.disable();
    this.modusSubscription! = this.service.modus$.subscribe(enabled => {
      this.direktorModus = enabled;
    //  this.direktorModus ? this.tourForm.enable() : this.tourForm.disable();
    });
  }

  ngOnInit() {
    if (this.tour == null){
      this.route.paramMap.subscribe(params => {

        this.service.getTour(params.get('id') as string).subscribe(data => {
        this.tour = data;
        console.log(this.tour);},
        error => {
          this.error = true;
          console.log("Error: " + error);})
      });
    }
  }
}
