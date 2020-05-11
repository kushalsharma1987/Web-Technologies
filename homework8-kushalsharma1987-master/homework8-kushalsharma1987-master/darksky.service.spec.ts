import { TestBed } from '@angular/core/testing';

import { DarkskyService } from './darksky.service';

describe('DarkskyService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DarkskyService = TestBed.get(DarkskyService);
    expect(service).toBeTruthy();
  });
});
