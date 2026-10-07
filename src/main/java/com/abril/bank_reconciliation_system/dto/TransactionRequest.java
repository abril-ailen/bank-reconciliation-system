package com.abril.bank_reconciliation_system.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {

    @NotNull(message = "must not be null")
    @Positive(message = "must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "must not be null")
    private LocalDate transactionDate;

    @NotBlank(message = "must not be blank")
    private String description;
}
