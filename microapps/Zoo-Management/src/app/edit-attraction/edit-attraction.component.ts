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
import {MatDialog} from "@angular/material/dialog";
import {MatIcon} from "@angular/material/icon";

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
    RouterOutlet,
    MatIcon
  ],
  templateUrl: './edit-attraction.component.html',
  styleUrl: './edit-attraction.component.scss'
})
export class EditAttractionComponent  implements OnInit{
  attraction: Attraction | undefined;
  attractionForm: FormGroup = new FormGroup({});
  protected loading= true;
  error= false;
  protected attractionName: null | string | undefined ;
  logo= '';

  constructor(private fb: FormBuilder, private service: ManagementService, private dialog: MatDialog) {
    this.attractionForm= this.fb.group({
      name: [''],
      logo: [''],description: [''],
      tags: this.fb.array([])
    });
  }

  refreshLogo() {
    this.logo = this.attractionForm.controls['logo'].value;
  }
  ngOnInit(): void {
    this.attractionName = this.getParameterByName('parameter');
    console.log(this.attractionName)
    if (this.attractionName != null) {
      this.service.getAttraction(this.attractionName).subscribe(attraction => {
        this.attraction = attraction;
        this.attractionForm.controls['name'].setValue(attraction.name);
        this.attractionForm.controls['logo'].setValue(attraction.logo);
        this.attractionForm.controls['description'].setValue(attraction.description);
        this.setTags(attraction);
        this.loading = false;
      }, error => {
        this.error = true;
        console.log("Error: " + error);
      });
    }
    else {
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
      name: [''],
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
      if(confirm("Are you sure to safe this Tour?")) {

        if (this.attractionName != null) {
        this.service.putAttraction(this.attractionForm.value, this.attractionName).subscribe({
          next: () => {
            console.log('Attraction changed!');

          },
          error: (error) => {
            console.error('Error sending Attraction:', error);
          }
        });
      } else if(this.attractionName == null) {
        this.service.postAttraction(this.attractionForm.value).subscribe({
          next: () => {
            console.log('Attraction added!');

          },
          error: (error) => {
            console.error('Error posting Attraction:', error);
          }
        });
      }
    }
    }  }

  deleteAttraction() {
    if(this.attractionName != null) {
      if(confirm("Are you sure you want to delete this Attraction?")) {
this.service.deleteAttraction(this.attractionName).subscribe({
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
}
