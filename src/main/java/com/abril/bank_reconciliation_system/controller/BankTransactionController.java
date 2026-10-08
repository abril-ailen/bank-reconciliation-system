package com.abril.bank_reconciliation_system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abril.bank_reconciliation_system.dto.BankTransactionRequest;
import com.abril.bank_reconciliation_system.dto.BankTransactionResponse;
import com.abril.bank_reconciliation_system.service.BankTransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bank-transactions")
public class BankTransactionController {

    private final BankTransactionService bankTransactionService;

    public BankTransactionController(BankTransactionService service) {
        this.bankTransactionService = service;
    }

    @PostMapping
    public ResponseEntity<BankTransactionResponse> createBankTransaction(
            @RequestBody @Valid BankTransactionRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(bankTransactionService.createBankTransaction(request));
    }

    @GetMapping
    public ResponseEntity<List<BankTransactionResponse>> getAllBankTransactions() {

        return ResponseEntity.ok(bankTransactionService.getAllBankTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankTransactionResponse> getBankTransactionById(@PathVariable Long id) {

        return ResponseEntity.ok(bankTransactionService.getBankTransactionById(id));
    }

}
