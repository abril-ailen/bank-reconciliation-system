package com.abril.bank_reconciliation_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abril.bank_reconciliation_system.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
