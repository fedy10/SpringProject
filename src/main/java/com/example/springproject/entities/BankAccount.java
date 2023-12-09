package com.example.springproject.entities;

import com.example.springproject.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//pour l'heritage
@DiscriminatorColumn(name = "TYPE",length = 4,discriminatorType = DiscriminatorType.STRING)
@Data @NoArgsConstructor @AllArgsConstructor
public class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus Status;
    @ManyToOne
    // plusieurs comptes pour un client
    private Customer customer;
    @OneToMany (mappedBy = "bankAccount")
    private List<AccountOperation> accountOperations;
}
