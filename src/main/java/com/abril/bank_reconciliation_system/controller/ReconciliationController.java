package com.abril.bank_reconciliation_system.controller;

import com.abril.bank_reconciliation_system.dto.ReconciliationResponse;
import com.abril.bank_reconciliation_system.service.ReconciliationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reconciliation")
public class ReconciliationController {

    private final ReconciliationService reconciliationService;

    public ReconciliationController(ReconciliationService reconciliationService) {
        this.reconciliationService = reconciliationService;
    }

    @PostMapping
    public ResponseEntity<ReconciliationResponse> reconcile() {
        return ResponseEntity.ok(reconciliationService.reconcile());
    }
}