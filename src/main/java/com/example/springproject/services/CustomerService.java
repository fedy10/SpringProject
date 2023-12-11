package com.example.springproject.services;

import com.example.springproject.dtos.BankAccountDTO;
import com.example.springproject.dtos.CurrentBankAccountDTO;
import com.example.springproject.dtos.CustomerDTO;
import com.example.springproject.dtos.SavingBankAccountDTO;
import com.example.springproject.exception.BalanceNotSufficientException;
import com.example.springproject.exception.BankAccountNotFoundException;
import com.example.springproject.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    CustomerDTO saveCustomer (CustomerDTO customer);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(Long customerId);
    List<CustomerDTO> ListCustomers();
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> searchCustomers(String keyword);
}
