package com.abril.bank_reconciliation_system.service;

import java.util.List;

import com.abril.bank_reconciliation_system.dto.TransactionRequest;
import com.abril.bank_reconciliation_system.dto.TransactionResponse;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest request);

    TransactionResponse getTransactionById(Long id);

    List<TransactionResponse> getAllTransactions();
}
