package com.example.springproject.services;

import com.example.springproject.dtos.CustomerDTO;
import com.example.springproject.entities.BankAccount;
import com.example.springproject.entities.CurrentAccount;
import com.example.springproject.entities.Customer;
import com.example.springproject.entities.SavingAccount;
import com.example.springproject.exception.BalanceNotSufficientException;
import com.example.springproject.exception.BankAccountNotFoundException;
import com.example.springproject.exception.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer (CustomerDTO customer);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    CurrentAccount saveCurrentBankAccount(double initialBalance , double overDraft , Long customerID) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initialBalance , double interestRate , Long customerID) throws CustomerNotFoundException;
    List<CustomerDTO> ListCustomers();
    BankAccount getBankAccount(String acountId) throws BankAccountNotFoundException;
    void debit(String accountId , double amount ,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId , double amount ,String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource , String accountIdDestination , double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
    List<BankAccount> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
}
