package com.example.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.SalaryCalculatorEngine
import com.example.model.CustomSalaryItem
import com.example.model.SalaryInput
import com.example.model.SalaryResult
import com.example.utils.AppLanguage
import com.example.utils.CurrencyFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SalaryUiState(
    val language: AppLanguage = AppLanguage.BANGLA,
    val basicSalaryText: String = "",
    val housingText: String = "",
    val medicalText: String = "",
    val transportText: String = "",
    val otherAllowanceText: String = "",
    val customAllowances: List<CustomSalaryItem> = emptyList(),

    val providentFundText: String = "",
    val incomeTaxText: String = "",
    val otherDeductionText: String = "",
    val customDeductions: List<CustomSalaryItem> = emptyList(),

    val result: SalaryResult = SalaryResult(),

    val showAddCustomAllowanceDialog: Boolean = false,
    val showAddCustomDeductionDialog: Boolean = false,
    val showResetConfirmDialog: Boolean = false,
    val showAboutDialog: Boolean = false
)

class SalaryViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("bd_salary_prefs", Context.MODE_PRIVATE)

    private val _uiState = MutableStateFlow(SalaryUiState())
    val uiState: StateFlow<SalaryUiState> = _uiState.asStateFlow()

    init {
        loadPersistedState()
    }

    private fun loadPersistedState() {
        val savedLangCode = prefs.getString("key_language", AppLanguage.BANGLA.code)
        val initialLanguage = if (savedLangCode == AppLanguage.ENGLISH.code) {
            AppLanguage.ENGLISH
        } else {
            AppLanguage.BANGLA
        }

        val basic = prefs.getString("key_basic", "") ?: ""
        val housing = prefs.getString("key_housing", "") ?: ""
        val medical = prefs.getString("key_medical", "") ?: ""
        val transport = prefs.getString("key_transport", "") ?: ""
        val otherAllow = prefs.getString("key_other_allow", "") ?: ""

        val pf = prefs.getString("key_pf", "") ?: ""
        val tax = prefs.getString("key_tax", "") ?: ""
        val otherDed = prefs.getString("key_other_ded", "") ?: ""

        _uiState.update { current ->
            val updated = current.copy(
                language = initialLanguage,
                basicSalaryText = basic,
                housingText = housing,
                medicalText = medical,
                transportText = transport,
                otherAllowanceText = otherAllow,
                providentFundText = pf,
                incomeTaxText = tax,
                otherDeductionText = otherDed
            )
            updated.copy(result = computeResult(updated))
        }
    }

    private fun persistValues() {
        val state = _uiState.value
        prefs.edit().apply {
            putString("key_language", state.language.code)
            putString("key_basic", state.basicSalaryText)
            putString("key_housing", state.housingText)
            putString("key_medical", state.medicalText)
            putString("key_transport", state.transportText)
            putString("key_other_allow", state.otherAllowanceText)
            putString("key_pf", state.providentFundText)
            putString("key_tax", state.incomeTaxText)
            putString("key_other_ded", state.otherDeductionText)
            apply()
        }
    }

    private fun computeResult(state: SalaryUiState): SalaryResult {
        val basic = CurrencyFormatter.parseToDouble(state.basicSalaryText)
        val housing = CurrencyFormatter.parseToDouble(state.housingText)
        val medical = CurrencyFormatter.parseToDouble(state.medicalText)
        val transport = CurrencyFormatter.parseToDouble(state.transportText)
        val otherAllow = CurrencyFormatter.parseToDouble(state.otherAllowanceText)

        val pf = CurrencyFormatter.parseToDouble(state.providentFundText)
        val tax = CurrencyFormatter.parseToDouble(state.incomeTaxText)
        val otherDed = CurrencyFormatter.parseToDouble(state.otherDeductionText)

        val input = SalaryInput(
            basicSalary = basic,
            housingAllowance = housing,
            medicalAllowance = medical,
            transportAllowance = transport,
            otherAllowance = otherAllow,
            customAllowances = state.customAllowances,
            providentFund = pf,
            incomeTax = tax,
            otherDeduction = otherDed,
            customDeductions = state.customDeductions
        )

        return SalaryCalculatorEngine.calculate(input)
    }

    fun setLanguage(language: AppLanguage) {
        _uiState.update { it.copy(language = language) }
        persistValues()
    }

    fun toggleLanguage() {
        val current = _uiState.value.language
        val next = if (current == AppLanguage.BANGLA) AppLanguage.ENGLISH else AppLanguage.BANGLA
        setLanguage(next)
    }

    fun updateBasicSalary(text: String) {
        _uiState.update {
            val updated = it.copy(basicSalaryText = text)
            updated.copy(result = computeResult(updated))
        }
        persistValues()
    }

    fun updateHousing(text: String) {
        _uiState.update {
            val updated = it.copy(housingText = text)
            updated.copy(result = computeResult(updated))
        }
        persistValues()
    }

    fun updateMedical(text: String) {
        _uiState.update {
            val updated = it.copy(medicalText = text)
            updated.copy(result = computeResult(updated))
        }
        persistValues()
    }

    fun updateTransport(text: String) {
        _uiState.update {
            val updated = it.copy(transportText = text)
            updated.copy(result = computeResult(updated))
        }
        persistValues()
    }

    fun updateOtherAllowance(text: String) {
        _uiState.update {
            val updated = it.copy(otherAllowanceText = text)
            updated.copy(result = computeResult(updated))
        }
        persistValues()
    }

    fun updateProvidentFund(text: String) {
        _uiState.update {
            val updated = it.copy(providentFundText = text)
            updated.copy(result = computeResult(updated))
        }
        persistValues()
    }

    fun updateIncomeTax(text: String) {
        _uiState.update {
            val updated = it.copy(incomeTaxText = text)
            updated.copy(result = computeResult(updated))
        }
        persistValues()
    }

    fun updateOtherDeduction(text: String) {
        _uiState.update {
            val updated = it.copy(otherDeductionText = text)
            updated.copy(result = computeResult(updated))
        }
        persistValues()
    }

    fun openAddCustomAllowanceDialog() {
        _uiState.update { it.copy(showAddCustomAllowanceDialog = true) }
    }

    fun closeAddCustomAllowanceDialog() {
        _uiState.update { it.copy(showAddCustomAllowanceDialog = false) }
    }

    fun addCustomAllowance(name: String, amount: Double) {
        _uiState.update {
            val list = it.customAllowances + CustomSalaryItem(name = name, amount = amount)
            val updated = it.copy(customAllowances = list, showAddCustomAllowanceDialog = false)
            updated.copy(result = computeResult(updated))
        }
    }

    fun removeCustomAllowance(id: String) {
        _uiState.update {
            val list = it.customAllowances.filterNot { item -> item.id == id }
            val updated = it.copy(customAllowances = list)
            updated.copy(result = computeResult(updated))
        }
    }

    fun openAddCustomDeductionDialog() {
        _uiState.update { it.copy(showAddCustomDeductionDialog = true) }
    }

    fun closeAddCustomDeductionDialog() {
        _uiState.update { it.copy(showAddCustomDeductionDialog = false) }
    }

    fun addCustomDeduction(name: String, amount: Double) {
        _uiState.update {
            val list = it.customDeductions + CustomSalaryItem(name = name, amount = amount)
            val updated = it.copy(customDeductions = list, showAddCustomDeductionDialog = false)
            updated.copy(result = computeResult(updated))
        }
    }

    fun removeCustomDeduction(id: String) {
        _uiState.update {
            val list = it.customDeductions.filterNot { item -> item.id == id }
            val updated = it.copy(customDeductions = list)
            updated.copy(result = computeResult(updated))
        }
    }

    fun showResetConfirmDialog() {
        // If data is significant, show dialog, else reset immediately
        val hasData = _uiState.value.basicSalaryText.isNotEmpty() ||
            _uiState.value.housingText.isNotEmpty() ||
            _uiState.value.customAllowances.isNotEmpty() ||
            _uiState.value.customDeductions.isNotEmpty()
        if (hasData) {
            _uiState.update { it.copy(showResetConfirmDialog = true) }
        } else {
            resetAll()
        }
    }

    fun dismissResetConfirmDialog() {
        _uiState.update { it.copy(showResetConfirmDialog = false) }
    }

    fun resetAll() {
        _uiState.update {
            val resetState = it.copy(
                basicSalaryText = "",
                housingText = "",
                medicalText = "",
                transportText = "",
                otherAllowanceText = "",
                customAllowances = emptyList(),
                providentFundText = "",
                incomeTaxText = "",
                otherDeductionText = "",
                customDeductions = emptyList(),
                showResetConfirmDialog = false
            )
            resetState.copy(result = computeResult(resetState))
        }
        persistValues()
    }

    fun openAboutDialog() {
        _uiState.update { it.copy(showAboutDialog = true) }
    }

    fun closeAboutDialog() {
        _uiState.update { it.copy(showAboutDialog = false) }
    }

    fun generateShareSummary(strings: com.example.utils.AppStrings): String {
        val state = _uiState.value
        val res = state.result
        val isBn = state.language == AppLanguage.BANGLA
        val fmt = { amt: Double -> CurrencyFormatter.formatCurrency(amt, isBn) }

        return buildString {
            appendLine("=== ${strings.appTitle} ===")
            appendLine("${strings.basicSalary}: ${fmt(res.basicSalary)}")
            if (res.totalAllowances > 0) {
                appendLine("${strings.allowancesSection}: ${fmt(res.totalAllowances)}")
            }
            appendLine("-------------------------")
            appendLine("${strings.grossSalary}: ${fmt(res.grossSalary)}")
            if (res.totalDeductions > 0) {
                appendLine("${strings.totalDeductions}: ${fmt(res.totalDeductions)}")
            }
            appendLine("-------------------------")
            appendLine("${strings.takeHomeSalary}: ${fmt(res.takeHomeSalary)}")
            appendLine("${strings.annualGross}: ${fmt(res.annualGross)}")
            appendLine("${strings.annualTakeHome}: ${fmt(res.annualTakeHome)}")
        }
    }
}
