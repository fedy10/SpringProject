package com.example.springproject.services;

import com.example.springproject.entities.*;
import com.example.springproject.enums.OperationType;
import com.example.springproject.exception.BalanceNotSufficientException;
import com.example.springproject.exception.BankAccountNotFoundException;
import com.example.springproject.exception.CustomerNotFoundException;
import com.example.springproject.reposiitories.AccountOperationRepository;
import com.example.springproject.reposiitories.BankAccountRepository;
import com.example.springproject.reposiitories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor // pour faire un constructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
       Customer savedCustomer= customerRepository.save(customer);
        log.info("Saving new Customer :"+savedCustomer+"at"+new Date());
        return savedCustomer;
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerID) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerID).orElse(null);
        if(customer==null){
            throw  new CustomerNotFoundException("Custommer not found");
        }
        CurrentAccount currentAccount=new CurrentAccount() ;
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        CurrentAccount savedBankAccount=bankAccountRepository.save(currentAccount);
        log.info("Saving new currentAccount :"+savedBankAccount+"at"+new Date());
        return savedBankAccount;
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerID) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerID).orElse(null);
        if(customer==null){
            throw  new CustomerNotFoundException("Custommer not found");
        }
        SavingAccount savingAccount=new SavingAccount() ;
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        SavingAccount savedBankAccount=bankAccountRepository.save(savingAccount);
        log.info("Saving new  savingAccount:"+savedBankAccount +"at"+new Date());
        return savedBankAccount;
    }


    @Override
    public List<Customer> ListCustomers() {
        log.info("Watching listCustomers at"+new Date());
        return customerRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String acountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(acountId).orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        log.info("Watching bank Account at"+new Date());
        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount=getBankAccount(accountId);
        if(bankAccount.getBalance()<amount){
            throw  new BalanceNotSufficientException("Balance not sufficient");
        }
        AccountOperation accountOperation =new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
        log.info("Debit :"+accountOperation +"at"+new Date());
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=getBankAccount(accountId);
        AccountOperation accountOperation =new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
        log.info("Credit :"+accountOperation +"at"+new Date());
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource,amount,"Transfer to : "+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from : "+accountIdSource);
    }
}
