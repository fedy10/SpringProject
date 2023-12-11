package com.example.springproject;

import com.example.springproject.dtos.BankAccountDTO;
import com.example.springproject.dtos.CustomerDTO;
import com.example.springproject.dtos.SavingBankAccountDTO;
import com.example.springproject.entities.*;
import com.example.springproject.enums.AccountStatus;
import com.example.springproject.enums.OperationType;
import com.example.springproject.exception.BalanceNotSufficientException;
import com.example.springproject.exception.BankAccountNotFoundException;
import com.example.springproject.exception.CustomerNotFoundException;
import com.example.springproject.reposiitories.AccountOperationRepository;
import com.example.springproject.reposiitories.BankAccountRepository;
import com.example.springproject.reposiitories.CustomerRepository;
import com.example.springproject.services.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringProjectApplication.class, args);
    }

   //@Bean
    // on va creer un nouvelle methode pour consulter les comptess
    CommandLineRunner consultercompte(BankService bankService){
        return args -> {
            bankService.consulter();
        };
    }
    @Bean //une fois on met l'annotation comme commentaire ou on la suprime la methode ne sera jamais executer
    CommandLineRunner start(CustomerServiceImpl customerService , BankAccountServiceImpl bankAccountService , AccountOperationServiceImpl accountOperationService){
        return args -> {
            Stream.of("fedy","maynou","akrouta").forEach(name->{
                CustomerDTO customer=new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerService.saveCustomer(customer);
            });
            //
            customerService.ListCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*9000,900,customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*1200,5.5, customer.getId());
                }catch (CustomerNotFoundException e){
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts=bankAccountService.bankAccountList();
            try{
                for (BankAccountDTO bankAccount:bankAccounts){
                    for(int i=0;i<5;i++){
                        accountOperationService.credit(bankAccount.getId(),1000+Math.random()*1500,"credit");

                    }
                    for(int i=0;i<5;i++){
                        accountOperationService.debit(bankAccount.getId(),100+Math.random()*1300,"credit");

                    }
                }
            }catch (BankAccountNotFoundException| BalanceNotSufficientException e){
                 e.printStackTrace();
            }
        };
    }

}
