import {Component, OnInit} from '@angular/core';
import {Metadata, Tour} from "../entites";
import {FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {ManagementService} from "../management.service";
import {CdkTextareaAutosize} from "@angular/cdk/text-field";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {NgForOf, NgIf} from "@angular/common";
import {RouterOutlet} from "@angular/router";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-edit-tour',
  standalone: true,
  imports: [
    CdkTextareaAutosize,
    FormsModule,
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    MatProgressSpinner,
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    RouterOutlet,
    MatIcon
  ],
  templateUrl: './edit-tour.component.html',
  styleUrl: './edit-tour.component.scss'
})
export class EditTourComponent implements OnInit {
  tour: Tour | undefined;
  tourForm: FormGroup = new FormGroup({});
  protected loading = true;
  error = false;
  protected tourName: null | string | undefined;
  logo= '';

  constructor(private fb: FormBuilder, private service: ManagementService) {
    this.tourForm = this.fb.group({
      name: [''],
      logo: [''],
      description: [''],
      price: [''],
      attractions: this.fb.array([]),
    });
  }
  refreshLogo() {
    this.logo = this.tourForm.controls['logo'].value;
  }
  ngOnInit(): void {
    console.log(window.location.href);
    this.tourName = this.getParameterByName('parameter');
    console.log(this.tourName)
    if (this.tourName != null) {
      this.service.getTour(this.tourName).subscribe(tour => {
        this.tour = tour;
        this.tourForm.controls['name'].setValue(tour.name);
        this.tourForm.controls['logo'].setValue(tour.logo);
        this.tourForm.controls['price'].setValue(tour.price);
        this.tourForm.controls['description'].setValue(tour.description);
        this.setAttractions(tour);
        this.loading = false;
      }, error => {
        this.error = true;
        console.log("Error: " + error);
      });
      ;
    } else {
      this.loading = false;
    }
  }

  getParameterByName(name: string) {
    const url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
      results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
  }

  private setAttractions(tour: Tour) {
    const attractionArray = this.tourForm.get('attractions') as FormArray;
    attractionArray.clear();

    for (let i = 0; i < tour.attractions.length; i++) {
      const attractionGroup = this.fb.group({
        attraction: [tour.attractions[i].attraction],
        begin: [tour.attractions[i].begin],
        end: [tour.attractions[i].end]
      });
      attractionArray.push(attractionGroup);
    }
  }

  get attractionForms() {
    return this.tourForm.get('attractions') as FormArray;
  }

  addAttraction() {
    const attractions = this.fb.group({
      tag: [''],
      begin: [''],
      end: ['']
    });

    this.attractionForms.push(attractions);
  }

  deleteAttraction(index: number) {
    this.attractionForms.removeAt(index);
  }

  getAttractionsControls() {
    return (this.tourForm.get('attractions') as FormArray).controls;
  }

  onSubmit() {
    if (this.tourForm.valid) {
      if (this.tourName != null) {

        this.service.putTour(this.tourForm.value, this.tourName).subscribe({
          next: () => {
            console.log('Tour changed!');

          },
          error: (error) => {
            console.error('Error sending Tour:', error);
          }
        });
      } else if (this.tourName == null) {
        this.service.postTour(this.tourForm.value).subscribe({
          next: () => {
            console.log('Tour added!');

          },
          error: (error) => {
            console.error('Error posting Tour:', error);
          }
        });
      }
    }
  }

  deleteTour() {
    if(this.tourName != null) {
      this.service.deleteTour(this.tourName).subscribe({
        next: () => {
          console.log('Attraction deleted!');
        },
        error: (error) => {
          console.error('Error deleting Attraction:', error);
        }
      });
    }
  }
}
