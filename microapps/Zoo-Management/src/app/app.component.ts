import {Component, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {ManagementService} from "./management.service";
import {HttpClientModule} from "@angular/common/http";
import {MatFormField, MatInput, MatLabel} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {Metadata} from "./entites";
import {CdkTextareaAutosize} from "@angular/cdk/text-field";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ReactiveFormsModule, NgIf, NgForOf, MatInput, MatLabel, MatFormField, MatButton, CdkTextareaAutosize],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'Zoo-Management';
  metadataForm: FormGroup | undefined;
  metadata: Metadata | undefined

  constructor(private fb: FormBuilder, private service: ManagementService) {
  }

  ngOnInit(): void {
    this.metadataForm = this.fb.group({
      name: ['', Validators.required],
      logo: ['', Validators.required],
      address: ['', Validators.required],
      description: ['', Validators.required],
      openeingWeekday: this.fb.array([]),
      openingHours: this.fb.array([]),
      openeingInfo: this.fb.array([]),
      prices: this.fb.array([]),
      category: this.fb.array([]),
    });
    this.service.getMetadata().subscribe(metadata => {
      this.metadata = metadata;
      this.metadataForm?.controls['name'].setValue(metadata.name);
      this.metadataForm?.controls['logo'].setValue(metadata.logo);
      this.metadataForm?.controls['address'].setValue(metadata.address);
      this.metadataForm?.controls['description'].setValue(metadata.description);
      this.setOpeningHours(metadata);
      this.setPrices(metadata);
      console.log(this.metadataForm?.value);
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
    // Hier kannst du die Logik für das Absenden des Formulars implementieren
    // z.B. POST-Anfrage an einen API-Endpunkt
    console.log(this.metadataForm?.value);

  }


  private setOpeningHours(metadata: Metadata) {
    for (let i = 0; i < metadata.openingHours.length; i++) {
      this.metadataForm?.controls['openingWeekday'].setValue(metadata.openingHours[i].weekday);
      this.metadataForm?.controls['openingInfo'].setValue(metadata.openingHours[i].info);
      this.metadataForm?.controls['openingHours'].setValue(metadata.openingHours[i].hours);
    }
    console.log(this.metadataForm?.value);
  }

  private setPrices(metadata: Metadata) {
    for (let i = 0; i < metadata.prices.length; i++) {
      this.metadataForm?.controls['category'].setValue(metadata.prices[i].category);
      this.metadataForm?.controls['prices'].setValue(metadata.prices[i].price);
    }

  }
}//
//for (let i = 0; i < metadata.openingHours.length; i++) {
//this.openingHours?.controls['weekday'].setValue(metadata.openingHours[i].weekday);
//this.openingHours?.controls['info'].setValue(metadata.openingHours[i].info);
//this.openingHours?.controls['hours'].setValue(metadata.openingHours[i].hours);
//}
//for (let i = 0; i < metadata.prices.length; i++) {
//this.prices?.controls['category'].setValue(metadata.prices[i].category);
//this.prices?.controls['price'].setValue(metadata.prices[i].price);
//}
