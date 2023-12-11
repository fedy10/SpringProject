package com.example.springproject.dtos;

import com.example.springproject.enums.OperationType;
import lombok.Data;
import java.util.Date;

@Data
public class AccountOperationDTO {
    private  long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private  String description;

}
