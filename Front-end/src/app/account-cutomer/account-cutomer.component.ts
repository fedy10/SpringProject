import { Component, OnInit } from '@angular/core';
import {Customer} from "../../../model/customer.model";
import {catchError, Observable, throwError} from "rxjs";
import {AccountDetails} from "../../../model/account.model";
import {FormBuilder, FormGroup} from "@angular/forms";
import {AccountsService} from "../services/accounts.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-account-cutomer',
  templateUrl: './account-cutomer.component.html',
  styleUrls: ['./account-cutomer.component.css']
})
export class AccountCutomerComponent implements OnInit {
  accountCustomerId!:string;
  accountObservable!:Observable<AccountDetails>;
  currentPage:number=0;
  pageSize:number=5;
  operationFormGroup!:FormGroup;
  errorMessage!:string;
  constructor(private form:FormBuilder ,private accountService:AccountsService,private route:ActivatedRoute ,private router:Router) {
    this.accountObservable=this.router.getCurrentNavigation()?.extras.state as Observable<AccountDetails>;
  }

  ngOnInit(): void {
    this.accountCustomerId=this.route.snapshot.params['id'];
    this.handleAccount();
    this.operationFormGroup=this.form.group({
      operationType:this.form.control(null),
      amount:this.form.control(0),
      description:this.form.control(null),
      accountDestination:this.form.control(null),
    })
  }

  handleAccount(){
    this.accountObservable=this.accountService.getAccount(this.accountCustomerId,this.currentPage,this.pageSize).pipe(
      catchError(err => {
        this.errorMessage=err.error.message;
        return throwError(err);
      })
    );
  }
  gotoPage(page: number) {
    this.currentPage=page;
    this.handleAccount();
  }

  handleAccountOperation() {
    console.log(this.accountCustomerId);
    let operationType=this.operationFormGroup.value.operationType;
    let amout:number=this.operationFormGroup.value.amount;
    let description:string=this.operationFormGroup.value.description;
    let accountDestination:string=this.operationFormGroup.value.accountDestination;
    if(operationType=='DEBIT'){
      this.accountService.debit(this.accountCustomerId,amout,description).subscribe({
        next:(data)=>{
          alert("Success Dabit");
          this.operationFormGroup.reset();
          this.handleAccount();
        },
        error:(err)=>{
          console.log(err);
        }
      });
    }else if(operationType=='CREDIT'){
      this.accountService.credit(this.accountCustomerId,amout,description).subscribe({
        next:(data)=>{
          alert("Success Credit");
          this.operationFormGroup.reset();
          this.handleAccount();
        },
        error:(err)=>{
          console.log(err);
        }
      });
    }else if(operationType=='TRANSFER'){
      this.accountService.transfer(this.accountCustomerId,accountDestination,amout,description).subscribe({
        next:(data)=>{
          alert("Success Transfer");
          this.operationFormGroup.reset();
          this.handleAccount();
        },
        error:(err)=>{
          console.log(err);
        }
      });
    }

  }
}
