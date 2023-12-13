import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Account, AccountDetails} from "../../../model/account.model";
import {Customer} from "../../../model/customer.model";

@Injectable({
  providedIn: 'root'
})
export class AccountsService {

  constructor(private http : HttpClient) { }

  public getAccount(accountId : string, page : number, size : number):Observable<AccountDetails>{
    return this.http.get<AccountDetails>(environment.backendHost+"/accounts/"+accountId+"/pageOperations?page="+page+"&size="+size);
  }
  public getCustomerAccounts(customerID:number):Observable<Array<Account>>{
    return this.http.get<Array<Account>>(environment.backendHost+"/customers/"+customerID+"/accounts")
  }
  public debit(accountId : string, amount : number, description:string){
    return this.http.post(environment.backendHost+"/accounts/debit",{accountId : accountId, amount : amount, description : description});
  }
  public credit(accountId : string, amount : number, description:string){
    return this.http.post(environment.backendHost+"/accounts/credit",{accountId : accountId, amount : amount, description : description});
  }
  public transfer(accountSource: string,accountDestination: string, amount : number, description:string){
    return this.http.post(environment.backendHost+"/accounts/transfer",{accountSource, accountDestination, amount, description });
  }
  public deleteAccount(id:string){
    return this.http.delete(environment.backendHost+"/accounts/"+id)
  }
}
