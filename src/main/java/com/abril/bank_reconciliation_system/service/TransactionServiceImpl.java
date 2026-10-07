package com.abril.bank_reconciliation_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abril.bank_reconciliation_system.dto.TransactionRequest;
import com.abril.bank_reconciliation_system.dto.TransactionResponse;
import com.abril.bank_reconciliation_system.entity.Transaction;
import com.abril.bank_reconciliation_system.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest request) {
        
        Transaction transaction = new Transaction();
        
        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setDescription(request.getDescription());

        transaction.setStatus("PENDING");
        Transaction saved = transactionRepository.save(transaction);

        return toResponse(saved);

    }

    @Override
    public TransactionResponse getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow();
        return toResponse(transaction);
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll().stream().map(this::toResponse).toList();
    }

    private TransactionResponse toResponse(Transaction transaction) {

        TransactionResponse response = new TransactionResponse();

        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setTransactionDate(transaction.getTransactionDate());
        response.setDescription(transaction.getDescription());
        response.setStatus(transaction.getStatus());

        return response;
    }
    
}