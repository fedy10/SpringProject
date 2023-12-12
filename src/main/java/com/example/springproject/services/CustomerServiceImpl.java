package com.example.springproject.services;

import com.example.springproject.dtos.BankAccountDTO;
import com.example.springproject.dtos.CurrentBankAccountDTO;
import com.example.springproject.dtos.CustomerDTO;
import com.example.springproject.dtos.SavingBankAccountDTO;
import com.example.springproject.entities.*;
import com.example.springproject.enums.OperationType;
import com.example.springproject.exception.BalanceNotSufficientException;
import com.example.springproject.exception.BankAccountNotFoundException;
import com.example.springproject.exception.CustomerNotFoundException;
import com.example.springproject.mappers.BankAccountMapperImpl;
import com.example.springproject.mappers.CustomerMapperImpl;
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
import java.util.stream.Stream;

@Service
@Transactional
@AllArgsConstructor // pour faire un constructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private CustomerMapperImpl customerMapper;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer =customerMapper.fomCustomerDTO(customerDTO);
        Customer savedCustomer= customerRepository.save(customer);
        log.info("Saving new Customer :"+savedCustomer+"at"+new Date());
        return customerMapper.fromCustomer(savedCustomer);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer customer =customerMapper.fomCustomerDTO(customerDTO);
        Customer savedCustomer= customerRepository.save(customer);
        log.info("Update a Customer :"+savedCustomer+"at"+new Date());
        return customerMapper.fromCustomer(savedCustomer);
    }
    @Override
    public void deleteCustomer(Long customerId){
        List<BankAccount> allByCustomerId = bankAccountRepository.findAllByCustomer_Id(customerId);
        for (BankAccount bankAccount : allByCustomerId) {
            String accountId = bankAccount.getId();
            List<AccountOperation> accountOperations = accountOperationRepository.deleteAllByBankAccount_Id(accountId);
        }
        bankAccountRepository.deleteAllByCustomer_Id(customerId);
        customerRepository.deleteById(customerId);
    }
    @Override
    public  CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return customerMapper.fromCustomer(customer);
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customers=customerRepository.searchCustomer(keyword);
        List<CustomerDTO> customerDTOS = customers.stream().map(cust -> customerMapper.fromCustomer(cust)).collect(Collectors.toList());
        return customerDTOS;
    }

    @Override
    public List<CustomerDTO> ListCustomers() {
        log.info("Watching listCustomers at"+new Date());
        List<Customer> customers= customerRepository.findAll(); // il va retourner des customer
        List<CustomerDTO> CustomerDTOs = customers.stream().map(customer -> customerMapper.fromCustomer(customer)).collect(Collectors.toList());// convertion
        return CustomerDTOs;
    }
}
