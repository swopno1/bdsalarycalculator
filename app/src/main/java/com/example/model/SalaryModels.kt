package com.example.model

import java.util.UUID

/**
 * Custom allowance or deduction added dynamically by user.
 */
data class CustomSalaryItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val amount: Double
)

/**
 * Complete input state for salary calculation.
 */
data class SalaryInput(
    val basicSalary: Double = 0.0,
    val housingAllowance: Double = 0.0,
    val medicalAllowance: Double = 0.0,
    val transportAllowance: Double = 0.0,
    val otherAllowance: Double = 0.0,
    val customAllowances: List<CustomSalaryItem> = emptyList(),

    val providentFund: Double = 0.0,
    val incomeTax: Double = 0.0,
    val otherDeduction: Double = 0.0,
    val customDeductions: List<CustomSalaryItem> = emptyList()
)

/**
 * Result of the salary calculation.
 */
data class SalaryResult(
    val basicSalary: Double = 0.0,
    val totalAllowances: Double = 0.0,
    val grossSalary: Double = 0.0,
    val totalDeductions: Double = 0.0,
    val takeHomeSalary: Double = 0.0,
    val annualGross: Double = 0.0,
    val annualTakeHome: Double = 0.0,
    val isDeductionExceedingGross: Boolean = false,
    val netSalaryPercentage: Double = 0.0
)
