package com.devsu.hackerearth.backend.account.model.dto;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class BankStatementDto {
    private Date date;
    private String client;
    private String accountNumber;
    private String accountType;
    private double initialAmount;
    @JsonProperty("isActive")
    private boolean active;
    private String transactionType;
    private double amount;
    private double balance;
}
