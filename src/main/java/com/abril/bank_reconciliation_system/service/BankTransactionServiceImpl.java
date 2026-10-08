package com.abril.bank_reconciliation_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abril.bank_reconciliation_system.dto.BankTransactionRequest;
import com.abril.bank_reconciliation_system.dto.BankTransactionResponse;
import com.abril.bank_reconciliation_system.entity.BankTransaction;
import com.abril.bank_reconciliation_system.exception.ResourceNotFoundException;
import com.abril.bank_reconciliation_system.repository.BankTransactionRepository;

@Service
public class BankTransactionServiceImpl implements BankTransactionService {

    private final BankTransactionRepository bankTransactionRepository;

    public BankTransactionServiceImpl(
            BankTransactionRepository bankTransactionRepository) {

        this.bankTransactionRepository = bankTransactionRepository;
    }

    @Override
    public BankTransactionResponse createBankTransaction(BankTransactionRequest request) {
        BankTransaction bankTransaction = new BankTransaction();

        bankTransaction.setAmount(request.getAmount());
        bankTransaction.setTransactionDate(request.getTransactionDate());
        bankTransaction.setReference(request.getReference());

        BankTransaction savedBankTransaction = bankTransactionRepository.save(bankTransaction);

        return toResponse(savedBankTransaction);
    }

    @Override
    public List<BankTransactionResponse> getAllBankTransactions() {

        return bankTransactionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override 
    public BankTransactionResponse getBankTransactionById(Long id){
        BankTransaction bankTransaction = bankTransactionRepository.findById(id).orElseThrow(
            ()-> new ResourceNotFoundException("Bank Transaction not found")
        );
        return toResponse(bankTransaction);
    }

    private BankTransactionResponse toResponse(BankTransaction bankTransaction) {

        BankTransactionResponse response = new BankTransactionResponse();

        response.setId(bankTransaction.getId());
        response.setAmount(bankTransaction.getAmount());
        response.setTransactionDate(bankTransaction.getTransactionDate());
        response.setReference(bankTransaction.getReference());

        return response;
    }

}
