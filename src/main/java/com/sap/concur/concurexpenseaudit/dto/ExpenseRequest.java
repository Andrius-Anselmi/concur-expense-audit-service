package com.sap.concur.concurexpenseaudit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequest {

    @NotBlank(message = "Transaction ID is mandatory")
    private String transactionId;

    @NotBlank(message = "Employee ID is mandatory")
    private String employeeId;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Currency code is mandatory")
    private String currencyCode;

    @NotBlank(message = "Category is mandatory")
    private String category;

    private String description;
}
