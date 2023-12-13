import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Customer} from "../../../model/customer.model";
import {Account, AccountDetails} from "../../../model/account.model";
import {catchError, map, Observable, throwError} from "rxjs";
import {CutomerService} from "../services/cutomer.service";
import {AccountsService} from "../services/accounts.service";

@Component({
  selector: 'app-customer-accounts',
  templateUrl: './customer-accounts.component.html',
  styleUrls: ['./customer-accounts.component.css']
})
export class CustomerAccountsComponent implements OnInit {
customerId!:number;
customer!:Customer;
erroMessage!:string;
customerAccounts!:Observable<Array<Account>>;

  constructor(private accountsService:AccountsService,private route:ActivatedRoute ,private router:Router ) {
    this.customer=this.router.getCurrentNavigation()?.extras.state as Customer;
  }

  ngOnInit(): void {
    this.customerId=this.route.snapshot.params['id'];
    this.customerAccounts=this.accountsService.getCustomerAccounts(this.customerId).pipe(
      catchError(err => {
        this.erroMessage=err.error.message;
        return throwError(err);
      })
    );
  }
  handleSearchCustomers(){

  }

  handleDeleteCustomerAccount(customerAccount: Account) {
    let conf=confirm("Are you sure ?")
    if(!conf) return;
    this.accountsService.deleteAccount(customerAccount.id).subscribe({
      next:resp=>{
        this.customerAccounts=this.customerAccounts.pipe(
          map(
            data=>{
              let index=data.indexOf(customerAccount);
              data.slice(index,1)
              return data;
            })
        );
      },
      error:err => {
        console.log(err);
      }
    })

  }

  handleUpdateCustomerAccount(customerAccount: Account) {
    this.router.navigateByUrl("/cutomser-update/"+customerAccount.id, {state:customerAccount});
  }

  handlAccounts(customerAccount: Account) {
    this.router.navigateByUrl("/account-customer/"+customerAccount.id, {state:customerAccount});
  }
}
