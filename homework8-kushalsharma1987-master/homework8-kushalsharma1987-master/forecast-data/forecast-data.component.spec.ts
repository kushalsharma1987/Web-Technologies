import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ForecastDataComponent } from './forecast-data.component';

describe('ForecastDataComponent', () => {
  let component: ForecastDataComponent;
  let fixture: ComponentFixture<ForecastDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ForecastDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ForecastDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
