package com.example.springproject.services;

import com.example.springproject.dtos.*;
import com.example.springproject.exception.BalanceNotSufficientException;
import com.example.springproject.exception.BankAccountNotFoundException;
import com.example.springproject.exception.CustomerNotFoundException;

import java.util.List;

public interface AccountOperationService {

    void debit(String accountId , double amount ,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId , double amount ,String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource , String accountIdDestination , double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<AccountOperationDTO> accountHistory(String accountId);
    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

}
