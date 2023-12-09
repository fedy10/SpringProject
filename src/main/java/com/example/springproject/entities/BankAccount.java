package com.example.springproject.entities;

import com.example.springproject.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data @NoArgsConstructor @AllArgsConstructor
public class BankAccount {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus Status;
    private Customer customer;
    private List<AccountOperation> accountOperations;
}
