package com.sap.concur.concurexpenseaudit.service;

import com.sap.concur.concurexpenseaudit.dto.AuditResponse;
import com.sap.concur.concurexpenseaudit.dto.ExpenseRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseAuditServiceTest {

    private AuditService auditService;

    @BeforeEach
    void setUp() {

        auditService = new ExpenseAuditServiceImpl();
    }

    @Test
    @DisplayName("Scenario 1: Should REJECT expense when category is ALCOHOL")
    void shouldRejectAlcoholCategory() {

        ExpenseRequest request = new ExpenseRequest();
        request.setCategory("ALCOHOL");
        request.setAmount(new BigDecimal("50.00"));

        AuditResponse response = auditService.auditExpense(request);

        assertEquals("REJECTED", response.getStatus());
        assertFalse(response.isApproved());
        assertEquals("Compliance Policy: Alcohol expenses are not reimbursable", response.getMessage());
    }

    @Test
    @DisplayName("Scenario 2: Should flag for MANAGER APPROVAL when amount >= 500")
    void shouldRequireManagerApprovalForHighValues() {

        ExpenseRequest request = new ExpenseRequest();
        request.setCategory("MEALS");
        request.setAmount(new BigDecimal("600.00"));


        AuditResponse response = auditService.auditExpense(request);


        assertEquals("PENDING_MANAGER_APPROVAL", response.getStatus());
        assertFalse(response.isApproved());
    }

    @Test
    @DisplayName("Scenario 3: Should AUTO-APPROVE valid expenses below 500")
    void shouldApproveAutomatically() {

        ExpenseRequest request = new ExpenseRequest();
        request.setCategory("TRANSPORT");
        request.setAmount(new BigDecimal("100.00"));


        AuditResponse response = auditService.auditExpense(request);


        assertEquals("APPROVED", response.getStatus());
        assertTrue(response.isApproved());
    }
}