package com.example.springproject.dtos;

import com.example.springproject.enums.AccountStatus;
import lombok.Data;

import java.util.Date;
@Data
public class BankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus Status;
    private CustomerDTO customerDTO;
    private String type;
}
