package com.abril.bank_reconciliation_system.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
public class TransactionRequest {

    @NotNull 
    @Positive
    private BigDecimal amount;

    @NotNull 
    private LocalDate transactionDate;

    @NotBlank 
    private String description;
}
