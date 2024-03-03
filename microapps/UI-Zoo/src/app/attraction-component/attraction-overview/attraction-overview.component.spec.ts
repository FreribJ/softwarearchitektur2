import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttractionOverviewComponent } from './attraction-overview.component';

describe('AttractionOverviewComponent', () => {
  let component: AttractionOverviewComponent;
  let fixture: ComponentFixture<AttractionOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AttractionOverviewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AttractionOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
