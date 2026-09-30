import { ComponentFixture, TestBed } from '@angular/core/testing';
import { StoryCatalog } from './story-catalog';

describe('StoryCatalog', () => {
  let component: StoryCatalog;
  let fixture: ComponentFixture<StoryCatalog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StoryCatalog]
    })
      .compileComponents();

    fixture = TestBed.createComponent(StoryCatalog);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
