package com.abril.bank_reconciliation_system.service;

import java.util.List;

import com.abril.bank_reconciliation_system.dto.BankTransactionRequest;
import com.abril.bank_reconciliation_system.dto.BankTransactionResponse;

public interface BankTransactionService {

    BankTransactionResponse createBankTransaction(BankTransactionRequest request);
    List<BankTransactionResponse> getAllBankTransactions();
    BankTransactionResponse getBankTransactionById(Long id);
}
