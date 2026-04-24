package com.sap.concur.concurexpenseaudit.service;

import com.sap.concur.concurexpenseaudit.dto.AuditResponse;
import com.sap.concur.concurexpenseaudit.dto.ExpenseRequest;

public interface AuditService {

    public AuditResponse auditExpense(ExpenseRequest expenseRequest);
}
