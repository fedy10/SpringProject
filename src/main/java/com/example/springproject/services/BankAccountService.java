package com.example.springproject.services;

import com.example.springproject.dtos.BankAccountDTO;
import com.example.springproject.dtos.CurrentBankAccountDTO;
import com.example.springproject.dtos.CustomerDTO;
import com.example.springproject.dtos.SavingBankAccountDTO;
import com.example.springproject.entities.BankAccount;
import com.example.springproject.entities.CurrentAccount;
import com.example.springproject.entities.Customer;
import com.example.springproject.entities.SavingAccount;
import com.example.springproject.exception.BalanceNotSufficientException;
import com.example.springproject.exception.BankAccountNotFoundException;
import com.example.springproject.exception.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {


    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerID) throws CustomerNotFoundException;

    CurrentBankAccountDTO updateCurrentBankAccount(double initialBalance, double overDraft, Long customerID) throws CustomerNotFoundException;

    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double intersetRate, Long customerID) throws CustomerNotFoundException;

    SavingBankAccountDTO updateSavingBankAccount(double initialBalance, double intersetRate, Long customerID) throws CustomerNotFoundException;

    BankAccountDTO getBankAccount(String acountId) throws BankAccountNotFoundException;
    List<BankAccountDTO> bankAccountList();

    void deleteBankAccount(String customerId);

}
