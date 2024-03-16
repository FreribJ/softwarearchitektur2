import {Component, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {CommonModule, NgForOf, NgIf} from "@angular/common";
import {ManagementService} from "./management.service";
import {MatFormField, MatInput, MatLabel} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {Metadata} from "./entites";
import {CdkTextareaAutosize} from "@angular/cdk/text-field";
import {MatProgressSpinner} from "@angular/material/progress-spinner";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterOutlet, ReactiveFormsModule, NgIf, NgForOf, MatInput, MatLabel, MatFormField, MatButton, CdkTextareaAutosize, MatProgressSpinner],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'Zoo-Management';
  metadata: Metadata | undefined;
  metadataForm: FormGroup = new FormGroup({});
  protected loading= true;

  constructor(private fb: FormBuilder, private service: ManagementService) {
    this.metadataForm = this.fb.group({
      name: ['', Validators.required],
      logo: ['', Validators.required],
      address: ['', Validators.required],
      description: ['', Validators.required],
      openingHours: this.fb.array([]),
      prices: this.fb.array([]),
    });}

  ngOnInit(): void {
    this.service.getMetadata().subscribe(metadata => {
      this.metadata = metadata;
      this.metadataForm.controls['name'].setValue(metadata.name);
      this.metadataForm.controls['logo'].setValue(metadata.logo);
      this.metadataForm.controls['address'].setValue(metadata.address);
      this.metadataForm.controls['description'].setValue(metadata.description);
      this.setOpeningHours(metadata);
      this.setPrices(metadata);
      this.loading = false;
    });
  }
  get openingHoursForms() {
    return this.metadataForm?.get('openingHours') as FormArray;
  }
  addOpeningHours() {
    const openingHours = this.fb.group({
      weekday: ['', Validators.required],
      hours: ['', Validators.required],
      info: ['', Validators.required]
    });
    this.openingHoursForms.push(openingHours);
  }
  deleteOpeningHours(index: number) {
    this.openingHoursForms.removeAt(index);
  }

  get pricesForms() {
    return this.metadataForm?.get('prices') as FormArray;
  }

  addPrice() {
    const price = this.fb.group({
      category: ['', Validators.required],
      price: ['', Validators.required]
    });

    this.pricesForms.push(price);
  }

  deletePrice(index: number) {
    this.pricesForms.removeAt(index);
  }

  onSubmit() {
    if (this.metadataForm.valid) {
      this.service.putMetadata(this.metadataForm.value).subscribe({
        next: () => {
          console.log('Metadata changed!');

        },
        error: (error) => {
          console.error('Error sending metadata:', error);
        }
      });
    }
  }


  private setOpeningHours(metadata: Metadata) {
    const openingHoursArray = this.metadataForm.get('openingHours') as FormArray;
    openingHoursArray.clear();

    for (let i = 0; i < metadata.openingHours.length; i++) {
      const openingHourGroup = this.fb.group({
        openingWeekday: [metadata.openingHours[i].weekday],
        openingInfo: [metadata.openingHours[i].info],
        openingHours: [metadata.openingHours[i].hours]
      });
      openingHoursArray.push(openingHourGroup);
    }

    console.log(this.metadataForm.value);
  }

  private setPrices(metadata: Metadata) {
    const pricesArray = this.metadataForm.get('prices') as FormArray;
    pricesArray.clear();

    for (let i = 0; i < metadata.prices.length; i++) {
      const priceGroup = this.fb.group({
        category: [metadata.prices[i].category],
        price: [metadata.prices[i].price]
      });
      pricesArray.push(priceGroup);
    }

    console.log(this.metadataForm.value);
  }

  getOpeningHoursControls() {
      return (this.metadataForm.get('openingHours') as FormArray).controls;  }

  getPricesControls() {
    return (this.metadataForm.get('prices') as FormArray).controls;
  }

}


//
//for (let i = 0; i < metadata.openingHours.length; i++) {
//this.openingHours?.controls['weekday'].setValue(metadata.openingHours[i].weekday);
//this.openingHours?.controls['info'].setValue(metadata.openingHours[i].info);
//this.openingHours?.controls['hours'].setValue(metadata.openingHours[i].hours);
//}
//for (let i = 0; i < metadata.prices.length; i++) {
//this.prices?.controls['category'].setValue(metadata.prices[i].category);
//this.prices?.controls['price'].setValue(metadata.prices[i].price);
//}
