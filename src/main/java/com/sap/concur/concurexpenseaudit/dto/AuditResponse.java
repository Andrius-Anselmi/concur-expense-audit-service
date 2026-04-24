package com.sap.concur.concurexpenseaudit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditResponse {

    private String transactionId;

    private boolean approved;

    private String status;

    private String message;

    private LocalDateTime auditDate;

}
