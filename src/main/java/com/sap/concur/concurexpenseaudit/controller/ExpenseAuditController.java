package com.sap.concur.concurexpenseaudit.controller;

import com.sap.concur.concurexpenseaudit.dto.AuditResponse;
import com.sap.concur.concurexpenseaudit.dto.ExpenseRequest;
import com.sap.concur.concurexpenseaudit.service.AuditService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/audit")
public class ExpenseAuditController {

    private final AuditService auditService;

    public ExpenseAuditController(AuditService auditService){
        this.auditService = auditService;
    }

    @PostMapping
    public ResponseEntity<AuditResponse> nomeGenerico(@Valid @RequestBody ExpenseRequest expenseRequest){
        AuditResponse response = auditService.auditExpense(expenseRequest);
        return ResponseEntity.ok(response);
    }

}
