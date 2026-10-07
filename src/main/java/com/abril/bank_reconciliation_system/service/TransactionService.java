package com.abril.bank_reconciliation_system.service;

import java.util.List;

import com.abril.bank_reconciliation_system.entity.Transaction;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);

    Transaction getTransactionById(Long id);

    List<Transaction> getAllTransactions();
}
