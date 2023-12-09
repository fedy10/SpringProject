package com.example.springproject.reposiitories;

import com.example.springproject.entities.AccountOperation;
import com.example.springproject.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
}
