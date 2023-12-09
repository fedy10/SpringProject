package com.example.springproject.entities;

import com.example.springproject.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    @ManyToOne
    private BankAccount bankAccount;
}
