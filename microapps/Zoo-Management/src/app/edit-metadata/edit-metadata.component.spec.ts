import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditMetadataComponent } from './edit-metadata.component';

describe('EditMetadataComponent', () => {
  let component: EditMetadataComponent;
  let fixture: ComponentFixture<EditMetadataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditMetadataComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditMetadataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
