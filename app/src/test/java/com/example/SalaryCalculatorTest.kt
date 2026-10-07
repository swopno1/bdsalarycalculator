package com.example

import com.example.calculator.SalaryCalculatorEngine
import com.example.model.CustomSalaryItem
import com.example.model.SalaryInput
import com.example.utils.CurrencyFormatter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SalaryCalculatorTest {

    @Test
    fun test1_basicOnly_noAllowances_noDeductions() {
        val input = SalaryInput(
            basicSalary = 30000.0,
            housingAllowance = 0.0,
            medicalAllowance = 0.0,
            transportAllowance = 0.0,
            otherAllowance = 0.0,
            providentFund = 0.0,
            incomeTax = 0.0,
            otherDeduction = 0.0
        )
        val result = SalaryCalculatorEngine.calculate(input)

        assertEquals(30000.0, result.grossSalary, 0.001)
        assertEquals(30000.0, result.takeHomeSalary, 0.001)
        assertEquals(360000.0, result.annualGross, 0.001)
        assertEquals(360000.0, result.annualTakeHome, 0.001)
        assertEquals(0.0, result.totalDeductions, 0.001)
        assertFalse(result.isDeductionExceedingGross)
    }

    @Test
    fun test2_allowancesAddition() {
        val input = SalaryInput(
            basicSalary = 30000.0,
            housingAllowance = 10000.0,
            medicalAllowance = 5000.0,
            transportAllowance = 5000.0
        )
        val result = SalaryCalculatorEngine.calculate(input)

        assertEquals(20000.0, result.totalAllowances, 0.001)
        assertEquals(50000.0, result.grossSalary, 0.001)
    }

    @Test
    fun test3_deductions_calculation() {
        val input = SalaryInput(
            basicSalary = 30000.0,
            housingAllowance = 10000.0,
            medicalAllowance = 5000.0,
            transportAllowance = 5000.0,
            providentFund = 3000.0,
            incomeTax = 1000.0
        )
        val result = SalaryCalculatorEngine.calculate(input)

        assertEquals(50000.0, result.grossSalary, 0.001)
        assertEquals(4000.0, result.totalDeductions, 0.001)
        assertEquals(46000.0, result.takeHomeSalary, 0.001)
        assertEquals(600000.0, result.annualGross, 0.001)
        assertEquals(552000.0, result.annualTakeHome, 0.001)
        assertFalse(result.isDeductionExceedingGross)
    }

    @Test
    fun test4_grossWithZeroDeductions() {
        val input = SalaryInput(
            basicSalary = 50000.0,
            providentFund = 0.0,
            incomeTax = 0.0
        )
        val result = SalaryCalculatorEngine.calculate(input)

        assertEquals(50000.0, result.grossSalary, 0.001)
        assertEquals(0.0, result.totalDeductions, 0.001)
        assertEquals(50000.0, result.takeHomeSalary, 0.001)
    }

    @Test
    fun test5_deductionsExceedGross_warningState() {
        val input = SalaryInput(
            basicSalary = 30000.0,
            providentFund = 25000.0,
            incomeTax = 10000.0 // total deductions = 35000 > 30000
        )
        val result = SalaryCalculatorEngine.calculate(input)

        assertEquals(30000.0, result.grossSalary, 0.001)
        assertEquals(35000.0, result.totalDeductions, 0.001)
        assertEquals(0.0, result.takeHomeSalary, 0.001)
        assertTrue(result.isDeductionExceedingGross)
    }

    @Test
    fun test6_customItemsIncludedInCalculation() {
        val input = SalaryInput(
            basicSalary = 40000.0,
            customAllowances = listOf(
                CustomSalaryItem(name = "Mobile Bill", amount = 1500.0),
                CustomSalaryItem(name = "Festival Bonus Allowance", amount = 8500.0)
            ),
            customDeductions = listOf(
                CustomSalaryItem(name = "Loan Repayment", amount = 5000.0)
            )
        )
        val result = SalaryCalculatorEngine.calculate(input)

        assertEquals(10000.0, result.totalAllowances, 0.001)
        assertEquals(50000.0, result.grossSalary, 0.001)
        assertEquals(5000.0, result.totalDeductions, 0.001)
        assertEquals(45000.0, result.takeHomeSalary, 0.001)
    }

    @Test
    fun test7_southAsianCurrencyFormatting() {
        assertEquals("500", CurrencyFormatter.formatSouthAsian(500.0))
        assertEquals("1,000", CurrencyFormatter.formatSouthAsian(1000.0))
        assertEquals("30,000", CurrencyFormatter.formatSouthAsian(30000.0))
        assertEquals("50,000", CurrencyFormatter.formatSouthAsian(50000.0))
        assertEquals("1,25,000", CurrencyFormatter.formatSouthAsian(125000.0))
        assertEquals("10,00,000", CurrencyFormatter.formatSouthAsian(1000000.0))
        assertEquals("1,00,00,000", CurrencyFormatter.formatSouthAsian(10000000.0))
    }

    @Test
    fun test8_bengaliDigitsAndParsing() {
        val formatted = CurrencyFormatter.formatCurrency(50000.0, isBangla = true)
        assertEquals("৳৫০,০০০", formatted)

        val lakhFormatted = CurrencyFormatter.formatCurrency(125000.0, isBangla = true)
        assertEquals("৳১,২৫,০০০", lakhFormatted)

        val parsedBn = CurrencyFormatter.parseToDouble("১,২৫,০০০")
        assertEquals(125000.0, parsedBn, 0.001)

        val parsedWithSymbol = CurrencyFormatter.parseToDouble("৳৫০,০০০")
        assertEquals(50000.0, parsedWithSymbol, 0.001)
    }
}
