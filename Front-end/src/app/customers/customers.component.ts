import { Component, OnInit } from '@angular/core';
import {CutomerService} from "../services/cutomer.service";
import {catchError, map, Observable, throwError} from "rxjs";
import {Customer} from "../../../model/customer.model";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {
  cutomers!:Observable<Array<Customer>>;
  //errorMessage:string|undefined;// variable null ( il faut faire la verfication toujours)
  erroMessage!:string;
  searchFormGroup:FormGroup |undefined;
  constructor(private customerService:CutomerService , private formBuilder:FormBuilder) { }

  ngOnInit(): void {
    this.searchFormGroup=this.formBuilder.group({
      keyword:this.formBuilder.control("")
    })

    this.handleSearchCustomers();
  }

  handleSearchCustomers() {
   let kw=this.searchFormGroup?.value.keyword;
   this.cutomers=this.customerService.searchCustomers(kw).pipe(
     catchError(err => {
       this.erroMessage=err.message;
       return throwError(err);
     })
   )
  }

  handleDeleteCustomer(c: Customer) {
    let conf=confirm("Are you sure ?")
      if(!conf) return;
    this.customerService.deleteCustomer(c.id).subscribe({
      next:resp=>{
        this.cutomers=this.cutomers.pipe(
          map(
            data=>{
              let index=data.indexOf(c);
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
}
