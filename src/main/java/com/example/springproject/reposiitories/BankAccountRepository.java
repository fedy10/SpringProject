package com.example.springproject.reposiitories;

import com.example.springproject.entities.BankAccount;
import com.example.springproject.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
