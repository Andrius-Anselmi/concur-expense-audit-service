package com.sap.concur.concurexpenseaudit.service;

import com.sap.concur.concurexpenseaudit.dto.AuditResponse;
import com.sap.concur.concurexpenseaudit.dto.ExpenseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ExpenseAuditServiceImpl implements AuditService {

    @Override
    public AuditResponse auditExpense(ExpenseRequest expenseRequest) {
        AuditResponse response = new AuditResponse();
        response.setTransactionId(expenseRequest.getTransactionId());
        response.setAuditDate(java.time.LocalDateTime.now());

        if(expenseRequest.getCategory().equalsIgnoreCase("ALCOHOL")){
            response.setStatus("REJECTED");
            response.setApproved(false);
            response.setMessage("Compliance Policy: Alcohol expenses are not reimbursable");
            return response;
        } else if(expenseRequest.getAmount().compareTo(new BigDecimal("500.00")) >= 0){
            response.setStatus("PENDING_MANAGER_APPROVAL");
            response.setApproved(false);
            response.setMessage("Value exceeds auto-approval limit. Pending manager review.");
            return response;

        }else{
            response.setStatus("APPROVED");
            response.setApproved(true);
            response.setMessage("Expense approved automatically");
            return response;
        }

    }
}
