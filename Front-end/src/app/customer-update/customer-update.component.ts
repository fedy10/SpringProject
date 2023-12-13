import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CutomerService} from "../services/cutomer.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Customer} from "../../../model/customer.model";
import {catchError, Observable, throwError} from "rxjs";

@Component({
  selector: 'app-customer-update',
  templateUrl: './customer-update.component.html',
  styleUrls: ['./customer-update.component.css']
})
export class CustomerUpdateComponent implements OnInit {
  newCustomerFormGroup!:FormGroup;
  customerId!:number;
  customer$!:Observable<Customer>;
  erroMessage!:string;
  constructor(private formgrp:FormBuilder , private customerService:CutomerService ,private router:Router,private route:ActivatedRoute) {
    this.customer$=this.router.getCurrentNavigation()?.extras.state as Observable<Customer>;
  }

  ngOnInit(): void {
    this.customerId=this.route.snapshot.params['id'];
    this.customer$=this.customerService.getCustomer(this.customerId).pipe(
      catchError(err => {
        this.erroMessage=err.error.message;
        return throwError(err);
      })
    );

    this.customer$.subscribe(customer => {
      this.newCustomerFormGroup = this.formgrp.group({
        name: [customer.name, [Validators.required ,Validators.minLength(4)]],
        email:[customer.email,[Validators.required,Validators.email]]
      });
    });

  }

  handleUpdateCustomer() {
    let customerName:string=this.newCustomerFormGroup.value.name;
    let customerEmail:string=this.newCustomerFormGroup.value.email;
    this.customerService.updateCustomer(this.customerId,customerName,customerEmail).subscribe({
      next:data=>{
        alert("Customer has been successfully saved !");
        //this.newCustomerFormGroup.reset(); // pour vider
        this.router.navigateByUrl("/customers")
      },
      error:err=>{
        console.log(err);
      }
    });

  }

}
