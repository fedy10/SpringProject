package com.example.springproject.mappers;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.example.springproject.dtos.BankAccountDTO;
import com.example.springproject.dtos.CurrentBankAccountDTO;
import com.example.springproject.dtos.CustomerDTO;
import com.example.springproject.dtos.SavingBankAccountDTO;
import com.example.springproject.entities.BankAccount;
import com.example.springproject.entities.CurrentAccount;
import com.example.springproject.entities.Customer;
import com.example.springproject.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    CustomerMapperImpl customerMapper =new CustomerMapperImpl();
    public BankAccountDTO formAccount(BankAccount bankAccount){
        BankAccountDTO bankAccountDTO=new BankAccountDTO();
        BeanUtils.copyProperties(bankAccount,bankAccountDTO);
        bankAccountDTO.setCustomerDTO(customerMapper.fromCustomer((bankAccount.getCustomer())));
        bankAccountDTO.setType(bankAccount.getClass().getTypeName());
        return bankAccountDTO;
    }
    public SavingBankAccountDTO fromSavingAccount(SavingAccount savingAccount){
        SavingBankAccountDTO savingBankAccountDTO=new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount,savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDTO(customerMapper.fromCustomer(savingAccount.getCustomer()));
        savingBankAccountDTO.setType(savingAccount.getClass().getTypeName());
        return savingBankAccountDTO;
    }
    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingAccountDTO){
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingAccount,savingAccountDTO);
        savingAccount.setCustomer(customerMapper.fomCustomerDTO(savingAccountDTO.getCustomerDTO()));
        return savingAccount;
    }
    public CurrentBankAccountDTO fromCurrentAccount(CurrentAccount currentAccount){
        CurrentBankAccountDTO currentBankAccountDTO=new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
        currentBankAccountDTO.setType(currentAccount.getClass().getTypeName());
        return currentBankAccountDTO;
    }
    public CurrentAccount fromSavingBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO){
        CurrentAccount cavingAccount=new CurrentAccount();
        BeanUtils.copyProperties(cavingAccount,currentBankAccountDTO);
        return cavingAccount;
    }

}
