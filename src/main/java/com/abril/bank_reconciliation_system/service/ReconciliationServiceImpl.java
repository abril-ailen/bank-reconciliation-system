package com.abril.bank_reconciliation_system.service;

import com.abril.bank_reconciliation_system.dto.ReconciliationResponse;
import com.abril.bank_reconciliation_system.entity.BankTransaction;
import com.abril.bank_reconciliation_system.entity.Transaction;
import com.abril.bank_reconciliation_system.repository.BankTransactionRepository;
import com.abril.bank_reconciliation_system.repository.TransactionRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class ReconciliationServiceImpl implements ReconciliationService {

    private final TransactionRepository transactionRepository;
    private final BankTransactionRepository bankTransactionRepository;

    public ReconciliationServiceImpl(TransactionRepository transactionRepository,
            BankTransactionRepository bankTransactionRepository) {

        this.transactionRepository = transactionRepository;
        this.bankTransactionRepository = bankTransactionRepository;
    }

    @Override
    public ReconciliationResponse reconcile() {
        List<Transaction> pendingTransactions = transactionRepository.findByStatus("PENDING");

        List<BankTransaction> bankTransactions = bankTransactionRepository.findAll();

        Set<Long> usedBankTransactionIds = new HashSet<>();

        int matchedCount = 0;
        int unmatchedCount = 0;

        for (Transaction transaction : pendingTransactions) {

            boolean foundMatch = false;

            for (BankTransaction bankTransaction : bankTransactions) {

                if (!usedBankTransactionIds.contains(bankTransaction.getId())
                        && transaction.getAmount().compareTo(bankTransaction.getAmount()) == 0
                        && transaction.getTransactionDate().equals(bankTransaction.getTransactionDate())) {
                    transaction.setStatus("MATCHED");
                    transaction.setBankTransaction(bankTransaction);
                    usedBankTransactionIds.add(bankTransaction.getId());
                    foundMatch = true;
                    matchedCount++;
                    break;
                }
            }
            if (!foundMatch) {
                transaction.setStatus("UNMATCHED");
                transaction.setBankTransaction(null);
                unmatchedCount++;
            }
            transactionRepository.save(transaction);
        }

        ReconciliationResponse response = new ReconciliationResponse();

        response.setTotalProcessed(pendingTransactions.size());
        response.setMatched(matchedCount);
        response.setUnmatched(unmatchedCount);

        return response;

    }
}