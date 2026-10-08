package com.abril.bank_reconciliation_system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReconciliationResponse {

    private int totalProcessed;
    private int matched;
    private int unmatched;
}