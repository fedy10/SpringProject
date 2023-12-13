import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomersComponent} from "./customers/customers.component";
import {AccountsComponent} from "./accounts/accounts.component";
import {NewCutomerComponent} from "./new-cutomer/new-cutomer.component";
import {CustomerUpdateComponent} from "./customer-update/customer-update.component";
import {CustomerAccountsComponent} from "./customer-accounts/customer-accounts.component";
import {AccountCutomerComponent} from "./account-cutomer/account-cutomer.component";

const routes: Routes = [
  {path:"customers" ,component:CustomersComponent},
  {path:"accounts",component:AccountsComponent},
  {path:"new-customer",component:NewCutomerComponent},
  {path:"customer-accoutns/:id" ,component:CustomerAccountsComponent},
  {path:"cutomser-update/:id",component:CustomerUpdateComponent},
  {path:"account-customer/:id",component:AccountCutomerComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
