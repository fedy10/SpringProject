package com.example.springproject.reposiitories;

import com.example.springproject.entities.AccountOperation;
import com.example.springproject.entities.BankAccount;
import com.example.springproject.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
    List<BankAccount> deleteAllByCustomer_Id(long customer);
    List<BankAccount> findAllByCustomer_Id(long customer);
}
