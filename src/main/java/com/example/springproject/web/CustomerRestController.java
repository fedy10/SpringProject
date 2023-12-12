package com.example.springproject.web;


import com.example.springproject.dtos.CustomerDTO;
import com.example.springproject.exception.CustomerNotFoundException;
import com.example.springproject.services.CustomerServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
//@RequestMapping
public class CustomerRestController {
    private CustomerServiceImpl customerService;
    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return customerService.ListCustomers();
    }
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return customerService.getCustomer(customerId);
    }
    @GetMapping("customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam (name = "keyword",defaultValue = "")String keyword){
        return customerService.searchCustomers("%"+keyword+"%");
    }
    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO request){
        return customerService.saveCustomer(request);
    }
    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable(name = "customerId") Long customerId,@RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return  customerService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/customers/{id}")
    public  void deleteCustomer(@PathVariable(name = "id") Long id){

        customerService.deleteCustomer(id);
    }
}
