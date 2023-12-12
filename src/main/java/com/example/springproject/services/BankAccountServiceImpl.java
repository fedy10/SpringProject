package com.example.springproject.services;

import com.example.springproject.dtos.BankAccountDTO;
import com.example.springproject.dtos.CurrentBankAccountDTO;
import com.example.springproject.dtos.SavingBankAccountDTO;
import com.example.springproject.entities.*;
import com.example.springproject.exception.BankAccountNotFoundException;
import com.example.springproject.exception.CustomerNotFoundException;
import com.example.springproject.mappers.AccountOperationMapperImpl;
import com.example.springproject.mappers.BankAccountMapperImpl;
import com.example.springproject.reposiitories.AccountOperationRepository;
import com.example.springproject.reposiitories.BankAccountRepository;
import com.example.springproject.reposiitories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor // pour faire un constructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private BankAccountMapperImpl bankAccountMapper;
    private AccountOperationRepository accountOperationRepository;
    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerID) throws CustomerNotFoundException {
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
        return bankAccountMapper.fromCurrentAccount(savedBankAccount);
    }
    @Override
    public CurrentBankAccountDTO updateCurrentBankAccount(double initialBalance, double overDraft, Long customerID) throws CustomerNotFoundException {
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
        return bankAccountMapper.fromCurrentAccount(savedBankAccount);
    }
    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double intersetRate, Long customerID) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerID).orElse(null);
        if(customer==null){
            throw  new CustomerNotFoundException("Custommer not found");
        }
        SavingAccount savingAccount=new SavingAccount() ;
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(intersetRate);
        SavingAccount savedBankAccount=bankAccountRepository.save(savingAccount);
        log.info("Saving new  savingAccount:"+savedBankAccount +"at"+new Date());
        System.out.println(savedBankAccount);
        return bankAccountMapper.fromSavingAccount(savedBankAccount);
    }
    @Override
    public SavingBankAccountDTO updateSavingBankAccount(double initialBalance, double intersetRate, Long customerID) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerID).orElse(null);
        if(customer==null){
            throw  new CustomerNotFoundException("Custommer not found");
        }
        SavingAccount savingAccount=new SavingAccount() ;
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(intersetRate);
        SavingAccount savedBankAccount=bankAccountRepository.save(savingAccount);
        log.info("Saving new  savingAccount:"+savedBankAccount +"at"+new Date());
        System.out.println(savedBankAccount);
        return bankAccountMapper.fromSavingAccount(savedBankAccount);
    }
    @Override
    public BankAccountDTO getBankAccount(String acountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(acountId).orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        log.info("Watching bank Account at"+new Date());
        if(bankAccount instanceof SavingAccount){
            SavingAccount savingAccount= (SavingAccount) bankAccount;
            return bankAccountMapper.fromSavingAccount(savingAccount);
        }
       else {
           CurrentAccount currentAccount=(CurrentAccount) bankAccount;
           return bankAccountMapper.fromCurrentAccount(currentAccount);
        }
    }

    @Override
    public List<BankAccountDTO> bankAccountList() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return bankAccountMapper.fromSavingAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return bankAccountMapper.fromCurrentAccount(currentAccount);
            }
        }).collect(Collectors.toList());
    return bankAccountDTOS;
    }
    @Override
    public void deleteBankAccount(String customerId){
        accountOperationRepository.deleteAllByBankAccount_Id(customerId);
        bankAccountRepository.deleteById(customerId);
    }
}
