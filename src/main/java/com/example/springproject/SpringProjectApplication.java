package com.example.springproject;

import com.example.springproject.entities.*;
import com.example.springproject.enums.AccountStatus;
import com.example.springproject.enums.OperationType;
import com.example.springproject.reposiitories.AccountOperationRepository;
import com.example.springproject.reposiitories.BankAccountRepository;
import com.example.springproject.reposiitories.CustomerRepository;
import com.example.springproject.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringProjectApplication.class, args);
    }

   @Bean
    // on va creer un nouvelle methode pour consulter les comptess
    CommandLineRunner consultercompte(BankService bankService){
        return args -> {
            bankService.consulter();
        };
    }
    //@Bean //une fois on met l'annotation comme commentaire ou on la suprime la methode ne sera jamais executer
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("fedy","amine","akrout").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust->{
                CurrentAccount currentAccount =new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());// chaine de caractere aleoatoirre
                currentAccount.setBalance(Math.random()*9000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount =new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*9000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(9000);
                bankAccountRepository.save(savingAccount);
            });

            // pour ajouter des operation ;
            bankAccountRepository.findAll().forEach(acc->{
                for (int i =0;i<5;i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random() * 1200);
                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }

}
