import {Component, OnInit} from '@angular/core';
import {CdkTextareaAutosize} from "@angular/cdk/text-field";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {CommonModule, NgForOf, NgIf} from "@angular/common";
import {FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Metadata} from "../entites";
import {ManagementService} from "../management.service";
import {RouterOutlet} from "@angular/router";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-edit-metadata',
  standalone: true,
  imports: [MatIcon,FormsModule, CommonModule, RouterOutlet, ReactiveFormsModule, NgIf, NgForOf, MatInput, MatLabel, MatFormField, MatButton, CdkTextareaAutosize, MatProgressSpinner],
  templateUrl: './edit-metadata.component.html',
  styleUrl: './edit-metadata.component.scss'
})
export class EditMetadataComponent implements OnInit {
  title = 'Zoo-Management';
  metadata: Metadata | undefined;
  metadataForm: FormGroup = new FormGroup({});
  protected loading= true;
  error= false;
  logo= '';


  constructor(private fb: FormBuilder, private service: ManagementService) {
    this.metadataForm = this.fb.group({
      name: [''],
      logo: [''],
      address: [''],
      description: [''],
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
      this.logo = metadata.logo;
      this.loading = false;
    }, error => {
      this.error = true;
      console.log("Error: " + error);
    });;
  }
  get openingHoursForms() {
    return this.metadataForm?.get('openingHours') as FormArray;
  }
  addOpeningHours() {
    const openingHours = this.fb.group({
      weekday: [''],
      hours: [''],
      info: ['']
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
      category: [''],
      price: ['']
    });

    this.pricesForms.push(price);
  }

  deletePrice(index: number) {
    this.pricesForms.removeAt(index);
  }

  onSubmit() {
    console.log("aufrug");
    if (this.metadataForm.valid && this.pricesForms.valid && this.openingHoursForms.valid) {
      console.log("weiter");

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

  refreshLogo() {
    this.logo = this.metadataForm.controls['logo'].value;
  }
}

