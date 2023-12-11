package com.example.springproject.mappers;

import com.example.springproject.dtos.AccountOperationDTO;
import com.example.springproject.entities.AccountOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountOperationMapperImpl {
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
      AccountOperationDTO accountOperationDTO =new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return accountOperationDTO;
    }
}
