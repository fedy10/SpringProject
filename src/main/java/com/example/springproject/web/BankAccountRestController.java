package com.example.springproject.web;

import com.example.springproject.dtos.*;
import com.example.springproject.exception.BankAccountNotFoundException;
import com.example.springproject.exception.CustomerNotFoundException;
import com.example.springproject.services.BankAccountService;
import com.example.springproject.services.BankAccountServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankAccountRestController {
    private BankAccountServiceImpl bankAccountService;

    @GetMapping("/accounts")
    public List<BankAccountDTO> lisAccount(){
        return bankAccountService.bankAccountList();
    }
    @GetMapping("/accounts/{id}")
    public BankAccountDTO getBankAccount(@PathVariable(name = "id") String accountId) throws BankAccountNotFoundException {
        return  bankAccountService.getBankAccount(accountId);
    }
    @PostMapping("/customers/{customerID}/save")
    public SavingBankAccountDTO saveSavingBankAccount(@RequestBody SavingBankAccountDTO request , @PathVariable (name = "customerID") Long customerID) throws CustomerNotFoundException {
        // Récupérer initialBalance et interestRate à partir de l'objet request
        double initialBalance = request.getBalance();
        double interestRate = request.getInterestRate();
        System.out.println(initialBalance);
        System.out.println(interestRate);
        return bankAccountService.saveSavingBankAccount(initialBalance,interestRate ,customerID);
    }
    @PutMapping("/customers/{customerID}/save/{idsavingcount}")
    public SavingBankAccountDTO updateSavingBankAccount(@RequestBody SavingBankAccountDTO request , @PathVariable (name = "customerID") Long customerID ,@PathVariable (name = "idsavingcount") String savingCountID ) throws CustomerNotFoundException {
        // Récupérer initialBalance et interestRate à partir de l'objet request
        double initialBalance = request.getBalance();
        double interestRate = request.getInterestRate();
        request.setId(savingCountID);
        return bankAccountService.updateSavingBankAccount(initialBalance,interestRate ,customerID);
    }
    @PostMapping("/customers/{customerID}/current")
    public CurrentBankAccountDTO currentBankAcount(@RequestBody CurrentBankAccountDTO request , @PathVariable (name = "customerID") Long customerID) throws CustomerNotFoundException {
        // Récupérer initialBalance et interestRate à partir de l'objet request
        double initialBalance = request.getBalance();
        double overDraft = request.getOverDraft();
        return bankAccountService.saveCurrentBankAccount(initialBalance,overDraft ,customerID);
    }
    @PutMapping("/customers/{customerID}/current/{idsavingcount}")
    public CurrentBankAccountDTO updateSavingBankAccount(@RequestBody CurrentBankAccountDTO request , @PathVariable (name = "customerID") Long customerID ,@PathVariable (name = "idsavingcount") String savingCountID ) throws CustomerNotFoundException {
        // Récupérer initialBalance et interestRate à partir de l'objet request
        double initialBalance = request.getBalance();
        double overDraft = request.getOverDraft();
        request.setId(savingCountID);
        return bankAccountService.updateCurrentBankAccount(initialBalance,overDraft ,customerID);
    }
    @DeleteMapping("/accounts/{id}")
    public  void deleteAccount(@PathVariable(name = "id") String id){

        bankAccountService.deleteBankAccount(id);
    }

}
