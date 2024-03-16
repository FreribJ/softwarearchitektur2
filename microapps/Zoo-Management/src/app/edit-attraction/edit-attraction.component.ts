import {Component, OnInit} from '@angular/core';
import {Attraction, Tour} from "../entites";
import {FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {ManagementService} from "../management.service";
import {CdkTextareaAutosize} from "@angular/cdk/text-field";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {NgForOf, NgIf} from "@angular/common";
import {RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-edit-attraction',
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
    RouterOutlet
  ],
  templateUrl: './edit-attraction.component.html',
  styleUrl: './edit-attraction.component.scss'
})
export class EditAttractionComponent  implements OnInit{
  attraction: Attraction | undefined;
  attractionForm: FormGroup = new FormGroup({});
  protected loading= true;

  constructor(private fb: FormBuilder, private service: ManagementService) {
    this.attractionForm= this.fb.group({
      name: ['', Validators.required],
      logo: ['', Validators.required],
      description: ['', Validators.required],
      tours: this.fb.array([]),
      tags: this.fb.array([]),

    });
  }
  ngOnInit(): void {
    const attractionName = this.getParameterByName('parameter');
    if (attractionName != null) {
      this.service.getAttraction(attractionName).subscribe(attraction => {
        this.attraction = attraction;
        this.attractionForm.controls['name'].setValue(attraction.name);
        this.attractionForm.controls['logo'].setValue(attraction.logo);
        this.attractionForm.controls['description'].setValue(attraction.description);
        this.setTours(attraction);
        this.setTags(attraction);
        this.loading = false;
      });
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

  private setTours(attraction: Attraction) {
    const tourArray = this.attractionForm.get('tours') as FormArray;
    tourArray.clear();

    for (let i = 0; i < attraction.nearestTourNames.length; i++) {
      const tourGroup = this.fb.group({
        tour: [attraction.nearestTourNames[i]]
      });
      tourArray.push(tourGroup);
    }
  }

  get tourForms() {
    return this.attractionForm.get('tours') as FormArray;
  }

  addTour() {
    const tour = this.fb.group({
      tourName: ['', Validators.required],
    });

    this.tourForms.push(tour);
  }

  deleteTour(index: number) {
    this.tourForms.removeAt(index);
  }
  getToursControls() {
    return (this.attractionForm.get('tours') as FormArray).controls;
  }

  private setTags(attraction: Attraction) {
    const tagArray = this.attractionForm.get('tags') as FormArray;
    tagArray.clear();

    for (let i = 0; i < attraction.tags.length; i++) {
      const tourGroup = this.fb.group({
        tagName: [attraction.tags[i]]
      });
      tagArray.push(tourGroup);
    }
  }

  get tagForms() {
    return this.attractionForm.get('tags') as FormArray;
  }

  addTag() {
    const tag = this.fb.group({
      name: ['', Validators.required],
    });

    this.tagForms.push(tag);
  }

  deleteTag(index: number) {
    this.tagForms.removeAt(index);
  }
  getTagsControls() {
    return (this.attractionForm.get('tags') as FormArray).controls;
  }

  onSubmit() {
    if (this.attractionForm.valid) {
      this.service.putTour(this.attractionForm.value).subscribe({
        next: () => {
          console.log('Tour changed!');

        },
        error: (error) => {
          console.error('Error sending Tour:', error);
        }
      });
    }
  }
}
