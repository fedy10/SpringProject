package com.example.springproject.mappers;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.example.springproject.dtos.CustomerDTO;
import com.example.springproject.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO=new CustomerDTO();
        /*customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());*/
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }
    public Customer fomCustomerDTO(CustomerDTO customerDTO){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }
}
