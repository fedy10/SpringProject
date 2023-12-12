import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { CustomersComponent } from './customers/customers.component';
import { AccountsComponent } from './accounts/accounts.component';
import { OperationComponent } from './operation/operation.component';
import {HttpClientModule} from "@angular/common/http";
import {ReactiveFormsModule} from "@angular/forms";
import { NewCutomerComponent } from './new-cutomer/new-cutomer.component';
import { CustomerAccountsComponent } from './customer-accounts/customer-accounts.component';
import { CustomerUpdateComponent } from './customer-update/customer-update.component';
import { AccountCutomerComponent } from './account-cutomer/account-cutomer.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    CustomersComponent,
    AccountsComponent,
    OperationComponent,
    NewCutomerComponent,
    CustomerAccountsComponent,
    CustomerUpdateComponent,
    AccountCutomerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
