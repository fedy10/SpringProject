package com.example.springproject.entities;

import com.example.springproject.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)//pour l'heritage
//@DiscriminatorColumn(name = "TYPE",length = 4,discriminatorType = DiscriminatorType.STRING)
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class BankAccount {
    //abstract pour ne pas creer un table bankAccount
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)// pour qu'il ne soit pas enrigester sous forme 0 , 1 ,2
    private AccountStatus Status;
    @ManyToOne
    // plusieurs comptes pour un client
    private Customer customer;
    @OneToMany (mappedBy = "bankAccount")
    private List<AccountOperation> accountOperations;
}
