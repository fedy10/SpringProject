package com.example.springproject.web;

import com.example.springproject.dtos.*;
import com.example.springproject.exception.BalanceNotSufficientException;
import com.example.springproject.exception.BankAccountNotFoundException;
import com.example.springproject.services.AccountOperationServiceImpl;
import com.example.springproject.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")

public class AccountOperationRestController {
    private AccountOperationServiceImpl accountOperationService;

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory (@PathVariable String accountId){

        return accountOperationService.accountHistory(accountId);
    }
    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "5")int size) throws BankAccountNotFoundException {
        return accountOperationService.getAccountHistory(accountId,page,size);
    }
    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        String accountId=debitDTO.getAccountId();
        System.out.println("éééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééééé");
        System.out.println(accountId);
        double amount=(double) debitDTO.getAmount();
        String description=debitDTO.getDescription();
        this.accountOperationService.debit(accountId,amount,description);
        return debitDTO;
    }
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
        String accountId=creditDTO.getAccountId();
        double amount=(double) creditDTO.getAmount();
        String description=creditDTO.getDescription();
        this.accountOperationService.credit(accountId,amount,description);
        return creditDTO;
    }
    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        String accountSource=transferRequestDTO.getAccountSource();
        String accountDestination=transferRequestDTO.getAccountDestination();
        double amount=(double) transferRequestDTO.getAmount();
        this.accountOperationService.transfer(
                accountSource,accountDestination,amount);
    }

}
