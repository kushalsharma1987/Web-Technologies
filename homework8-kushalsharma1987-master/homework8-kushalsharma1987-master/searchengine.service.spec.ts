import { TestBed } from '@angular/core/testing';

import { SearchengineService } from './searchengine.service';

describe('SearchengineService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SearchengineService = TestBed.get(SearchengineService);
    expect(service).toBeTruthy();
  });
});
