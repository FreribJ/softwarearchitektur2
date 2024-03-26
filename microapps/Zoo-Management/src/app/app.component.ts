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
import { Router } from '@angular/router';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterOutlet, ReactiveFormsModule, NgIf, NgForOf, MatInput, MatLabel, MatFormField, MatButton, CdkTextareaAutosize, MatProgressSpinner],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {

  constructor(private router: Router) {
  }
}
