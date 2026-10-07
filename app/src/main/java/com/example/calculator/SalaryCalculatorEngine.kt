package com.example.calculator

import com.example.model.SalaryInput
import com.example.model.SalaryResult
import kotlin.math.max

object SalaryCalculatorEngine {

    /**
     * Pure calculation engine for Bangladesh salary calculations.
     * Guaranteed non-negative, overflow-safe, and deterministic.
     */
    fun calculate(input: SalaryInput): SalaryResult {
        val basic = max(0.0, input.basicSalary)
        val housing = max(0.0, input.housingAllowance)
        val medical = max(0.0, input.medicalAllowance)
        val transport = max(0.0, input.transportAllowance)
        val otherAllow = max(0.0, input.otherAllowance)
        val customAllowTotal = input.customAllowances.sumOf { max(0.0, it.amount) }

        val totalAllowances = housing + medical + transport + otherAllow + customAllowTotal
        val grossSalary = basic + totalAllowances

        val pf = max(0.0, input.providentFund)
        val tax = max(0.0, input.incomeTax)
        val otherDed = max(0.0, input.otherDeduction)
        val customDedTotal = input.customDeductions.sumOf { max(0.0, it.amount) }

        val totalDeductions = pf + tax + otherDed + customDedTotal
        val isDeductionExceedingGross = totalDeductions > grossSalary

        // Do not silently produce negative take-home salary
        val takeHomeSalary = if (isDeductionExceedingGross) {
            0.0
        } else {
            grossSalary - totalDeductions
        }

        val annualGross = grossSalary * 12.0
        val annualTakeHome = takeHomeSalary * 12.0

        val netPercentage = if (grossSalary > 0.0) {
            (takeHomeSalary / grossSalary) * 100.0
        } else {
            0.0
        }

        return SalaryResult(
            basicSalary = basic,
            totalAllowances = totalAllowances,
            grossSalary = grossSalary,
            totalDeductions = totalDeductions,
            takeHomeSalary = takeHomeSalary,
            annualGross = annualGross,
            annualTakeHome = annualTakeHome,
            isDeductionExceedingGross = isDeductionExceedingGross,
            netSalaryPercentage = netPercentage
        )
    }
}
