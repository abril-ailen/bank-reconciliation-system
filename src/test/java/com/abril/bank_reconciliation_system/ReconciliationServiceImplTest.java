package com.abril.bank_reconciliation_system;

import com.abril.bank_reconciliation_system.entity.BankTransaction;
import com.abril.bank_reconciliation_system.entity.Transaction;
import com.abril.bank_reconciliation_system.repository.BankTransactionRepository;
import com.abril.bank_reconciliation_system.repository.TransactionRepository;
import com.abril.bank_reconciliation_system.service.ReconciliationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReconciliationServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private BankTransactionRepository bankTransactionRepository;

    private ReconciliationServiceImpl reconciliationService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        reconciliationService = new ReconciliationServiceImpl(transactionRepository, bankTransactionRepository);
    }

    @Test
    void shouldMatchTransactionWhenAmountAndDateAreEqual() {

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(new BigDecimal("10000.00"));
        transaction.setTransactionDate(LocalDate.of(2026, 9, 15));
        transaction.setDescription("Test transaction");
        transaction.setStatus("PENDING");

        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setId(1L);
        bankTransaction.setAmount(new BigDecimal("10000.00"));
        bankTransaction.setTransactionDate(LocalDate.of(2026, 9, 15));
        bankTransaction.setReference("BANK-TEST-001");

        when(transactionRepository.findByStatus("PENDING"))
                .thenReturn(List.of(transaction));

        when(bankTransactionRepository.findAll())
                .thenReturn(List.of(bankTransaction));

        reconciliationService.reconcile();

        assertEquals("MATCHED", transaction.getStatus());
        assertEquals(bankTransaction, transaction.getBankTransaction());
    }

    @Test
    void shouldMarkTransactionAsUnmatchedWhenNoBankTransactionMatches() {

        Transaction transaction = new Transaction();
        transaction.setId(2L);
        transaction.setAmount(new BigDecimal("15000.00"));
        transaction.setTransactionDate(LocalDate.of(2026, 9, 16));
        transaction.setDescription("Unmatched test");
        transaction.setStatus("PENDING");

        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setId(2L);
        bankTransaction.setAmount(new BigDecimal("20000.00"));
        bankTransaction.setTransactionDate(LocalDate.of(2026, 9, 16));
        bankTransaction.setReference("BANK-TEST-002");

        when(transactionRepository.findByStatus("PENDING"))
                .thenReturn(List.of(transaction));

        when(bankTransactionRepository.findAll())
                .thenReturn(List.of(bankTransaction));

        reconciliationService.reconcile();

        assertEquals("UNMATCHED", transaction.getStatus());
        assertNull(transaction.getBankTransaction());
    }

    @Test
    void shouldNotReuseBankTransactionForMultipleTransactions() {

        Transaction transactionA = new Transaction();
        transactionA.setId(1L);
        transactionA.setAmount(new BigDecimal("20000.00"));
        transactionA.setTransactionDate(LocalDate.of(2026, 9, 20));
        transactionA.setDescription("Duplicate A");
        transactionA.setStatus("PENDING");

        Transaction transactionB = new Transaction();
        transactionB.setId(2L);
        transactionB.setAmount(new BigDecimal("20000.00"));
        transactionB.setTransactionDate(LocalDate.of(2026, 9, 20));
        transactionB.setDescription("Duplicate B");
        transactionB.setStatus("PENDING");

        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setId(1L);
        bankTransaction.setAmount(new BigDecimal("20000.00"));
        bankTransaction.setTransactionDate(LocalDate.of(2026, 9, 20));
        bankTransaction.setReference("BANK-TEST-003");

        when(transactionRepository.findByStatus("PENDING"))
                .thenReturn(List.of(transactionA, transactionB));

        when(bankTransactionRepository.findAll())
                .thenReturn(List.of(bankTransaction));

        reconciliationService.reconcile();

        assertEquals("MATCHED", transactionA.getStatus());
        assertEquals("UNMATCHED", transactionB.getStatus());

        assertEquals(bankTransaction, transactionA.getBankTransaction());
        assertNull(transactionB.getBankTransaction());
    }

}