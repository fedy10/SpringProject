package com.example.springproject.entities;

import com.example.springproject.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data @AllArgsConstructor @NoArgsConstructor
public class AccountOperation {
    private  long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private BankAccount bankAccount;
}
