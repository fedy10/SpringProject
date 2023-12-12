import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Customer} from "../../../model/customer.model";
import {CutomerService} from "../services/cutomer.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-new-cutomer',
  templateUrl: './new-cutomer.component.html',
  styleUrls: ['./new-cutomer.component.css']
})
export class NewCutomerComponent implements OnInit {

  newCustomerFormGroup!:FormGroup;
  constructor(private formgrp:FormBuilder , private customerService:CutomerService ,private router:Router) { }

  ngOnInit(): void {
    this.newCustomerFormGroup=this.formgrp.group({
      name:this.formgrp.control(null,[Validators.required ,Validators.minLength(4)]),
      email:this.formgrp.control(null,[Validators.required,Validators.email]),
    });
  }

  handleSaveCustomer() {
    let customer:Customer=this.newCustomerFormGroup.value;
    this.customerService.saveCustomer(customer).subscribe({
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
