import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AccountsService} from "../services/accounts.service";
import {Observable} from "rxjs";
import {AccountDetails} from "../../../model/account.model";

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {

  accountFormGroup!:FormGroup;
  currentPage:number=0;
  pageSize:number=5;
  accountObservable!:Observable<AccountDetails>
  operationFormGroup!:FormGroup;
  constructor(private form:FormBuilder ,private accountService:AccountsService) { }

  ngOnInit(): void {
    this.accountFormGroup=this.form.group({
      accountId:this.form.control('')
    });
    this.operationFormGroup=this.form.group({
      operationType:this.form.control(null),
      amount:this.form.control(0),
      description:this.form.control(null),
      accountDestination:this.form.control(null),
    })
  }

  handleSearchAccount() {
    let accoundId:string=this.accountFormGroup.value.accountId;
    this.accountObservable=this.accountService.getAccount(accoundId,this.currentPage,this.pageSize);
  }

  gotoPage(page: number) {
    this.currentPage=page;
    this.handleSearchAccount();
  }

  handleAccountOperation() {
    console.log(this.operationFormGroup.value);
    let accountId:string=this.accountFormGroup.value.accountId;
    let operationType=this.operationFormGroup.value.operationType;
    let amout:number=this.operationFormGroup.value.amount;
    let description:string=this.operationFormGroup.value.description;
    let accountDestination:string=this.operationFormGroup.value.accountDestination;
    if(operationType=='DEBIT'){
      this.accountService.debit(accountId,amout,description).subscribe({
        next:(data)=>{
          alert("Success Dabit");
          this.handleSearchAccount();
        },
        error:(err)=>{
          console.log(err);
      }
      });
    }else if(operationType=='CREDIT'){
      this.accountService.credit(accountId,amout,description).subscribe({
        next:(data)=>{
          alert("Success Credit");
          this.handleSearchAccount();
        },
        error:(err)=>{
          console.log(err);
        }
      });
    }else if(operationType=='TRANSFER'){
      this.accountService.transfer(accountId,accountDestination,amout,description).subscribe({
        next:(data)=>{
          alert("Success Transfer");
          this.handleSearchAccount();
        },
        error:(err)=>{
          console.log(err);
        }
      });
    }
  }
}
