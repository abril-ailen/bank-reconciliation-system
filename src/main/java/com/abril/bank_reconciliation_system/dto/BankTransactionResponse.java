package com.abril.bank_reconciliation_system.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class BankTransactionResponse {

    private Long id;

    private BigDecimal amount;
    private LocalDate transactionDate;
    private String reference;

}
