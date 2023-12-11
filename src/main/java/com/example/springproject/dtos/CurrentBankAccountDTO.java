package com.example.springproject.dtos;

import com.example.springproject.enums.AccountStatus;
import lombok.Data;

import java.util.Date;

@Data
public class CurrentBankAccountDTO  extends  BankAccountDTO{
    private double overDraft;
}
