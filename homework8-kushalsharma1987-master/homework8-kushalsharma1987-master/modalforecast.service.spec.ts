import { TestBed } from '@angular/core/testing';

import { ModalforecastService } from './modalforecast.service';

describe('ModalforecastService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ModalforecastService = TestBed.get(ModalforecastService);
    expect(service).toBeTruthy();
  });
});
