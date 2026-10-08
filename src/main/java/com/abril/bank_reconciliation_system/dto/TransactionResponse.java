package com.abril.bank_reconciliation_system.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionResponse {

    private Long id;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private String description;
    private String status;
    private Long bankTransactionId;
}
