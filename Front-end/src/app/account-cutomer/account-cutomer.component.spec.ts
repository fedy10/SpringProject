import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountCutomerComponent } from './account-cutomer.component';

describe('AccountCutomerComponent', () => {
  let component: AccountCutomerComponent;
  let fixture: ComponentFixture<AccountCutomerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountCutomerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountCutomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
