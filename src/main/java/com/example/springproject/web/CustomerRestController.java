package com.example.springproject.web;

import com.example.springproject.dtos.CustomerDTO;
import com.example.springproject.entities.Customer;
import com.example.springproject.exception.CustomerNotFoundException;
import com.example.springproject.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
//@RequestMapping
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.ListCustomers();
    }
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }
    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO request){
        return bankAccountService.saveCustomer(request);
    }
    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable(name = "customerId") Long customerId,@RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return  bankAccountService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/customers/{id}")
    public  void deleteCustomer(@PathVariable(name = "id") Long id){
        bankAccountService.deleteCustomer(id);
    }
}
