package com.example.springproject.services;

import com.example.springproject.dtos.AccountHistoryDTO;
import com.example.springproject.dtos.AccountOperationDTO;
import com.example.springproject.entities.*;
import com.example.springproject.enums.OperationType;
import com.example.springproject.exception.BalanceNotSufficientException;
import com.example.springproject.exception.BankAccountNotFoundException;
import com.example.springproject.mappers.AccountOperationMapperImpl;
import com.example.springproject.reposiitories.AccountOperationRepository;
import com.example.springproject.reposiitories.BankAccountRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor // pour faire un constructor
@Slf4j
public class AccountOperationServiceImpl implements AccountOperationService {
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountServiceImpl bankAccountService;
    private AccountOperationMapperImpl accountOperationMapper;


    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount= bankAccountService.getBanKAccount (accountId);
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
        BankAccount bankAccount= bankAccountService.getBanKAccount (accountId);
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
    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccount_Id(accountId);
        return accountOperations.stream().map(oppreation->accountOperationMapper.fromAccountOperation(oppreation)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null) throw new BankAccountNotFoundException("Account not Found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> accountOperationMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }
}
