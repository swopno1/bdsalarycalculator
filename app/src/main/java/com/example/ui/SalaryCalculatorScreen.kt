package com.example.ui

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CustomSalaryItem
import com.example.ui.components.AddCustomItemDialog
import com.example.ui.components.SalaryBreakdownCard
import com.example.ui.components.SalaryInputField
import com.example.ui.components.SalaryResultCard
import com.example.utils.AppLanguage
import com.example.utils.CurrencyFormatter
import com.example.utils.LocalizationManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalaryCalculatorScreen(
    viewModel: SalaryViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val strings = LocalizationManager.getStrings(state.language)
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val isBangla = state.language == AppLanguage.BANGLA

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = CurrencyFormatter.CURRENCY_SYMBOL,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                ),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = strings.appTitle,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = if (isBangla) "বাংলাদেশ পে-রোল ক্যালকুলেটর" else "Bangladesh Payroll Calculator",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                actions = {
                    // Language Switcher Chip
                    Surface(
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { viewModel.toggleLanguage() }
                            .testTag("language_toggle_button"),
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Language,
                                contentDescription = "Switch Language",
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (state.language == AppLanguage.BANGLA) "English" else "বাংলা",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }

                    // Share button
                    IconButton(
                        onClick = {
                            val summary = viewModel.generateShareSummary(strings)
                            clipboardManager.setText(AnnotatedString(summary))
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, summary)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, strings.shareBreakdown)
                            context.startActivity(shareIntent)
                        },
                        modifier = Modifier.testTag("share_summary_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = strings.shareBreakdown,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Reset icon button
                    IconButton(
                        onClick = { viewModel.showResetConfirmDialog() },
                        modifier = Modifier.testTag("header_reset_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = strings.reset,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.widthIn(max = 640.dp)) {
                        // Focus Result Card
                        SalaryResultCard(
                            result = state.result,
                            strings = strings,
                            language = state.language
                        )
                    }
                }
            }

            // Main Input Sections in responsive centered container
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.widthIn(max = 640.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Basic Salary Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Outlined.Payments,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = strings.basicSalary,
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                                    ) {
                                        Text(
                                            text = strings.basicSalaryRequired,
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                SalaryInputField(
                                    label = strings.basicSalary,
                                    value = state.basicSalaryText,
                                    onValueChange = { viewModel.updateBasicSalary(it) },
                                    placeholder = strings.basicSalaryHint,
                                    testTag = "input_basic_salary",
                                    isRequired = true
                                )
                            }
                        }

                        // Allowances Section Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Outlined.Calculate,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.tertiary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = strings.allowancesSection,
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                    Text(
                                        text = strings.optionalZeroDefault,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                SalaryInputField(
                                    label = strings.housingAllowance,
                                    value = state.housingText,
                                    onValueChange = { viewModel.updateHousing(it) },
                                    testTag = "input_housing_allowance"
                                )

                                SalaryInputField(
                                    label = strings.medicalAllowance,
                                    value = state.medicalText,
                                    onValueChange = { viewModel.updateMedical(it) },
                                    testTag = "input_medical_allowance"
                                )

                                SalaryInputField(
                                    label = strings.transportAllowance,
                                    value = state.transportText,
                                    onValueChange = { viewModel.updateTransport(it) },
                                    testTag = "input_transport_allowance"
                                )

                                SalaryInputField(
                                    label = strings.otherAllowance,
                                    value = state.otherAllowanceText,
                                    onValueChange = { viewModel.updateOtherAllowance(it) },
                                    testTag = "input_other_allowance"
                                )

                                // Custom added allowances
                                state.customAllowances.forEach { item ->
                                    CustomItemRow(
                                        item = item,
                                        isBangla = isBangla,
                                        onRemove = { viewModel.removeCustomAllowance(item.id) },
                                        testTagPrefix = "custom_allowance"
                                    )
                                }

                                // Add Allowance Button
                                TextButton(
                                    onClick = { viewModel.openAddCustomAllowanceDialog() },
                                    modifier = Modifier.testTag("add_allowance_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = strings.addAllowance,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }
                        }

                        // Deductions Section Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Outlined.AccountBalance,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.error,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = strings.deductionsSection,
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                    Text(
                                        text = strings.optionalZeroDefault,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                SalaryInputField(
                                    label = strings.providentFund,
                                    value = state.providentFundText,
                                    onValueChange = { viewModel.updateProvidentFund(it) },
                                    testTag = "input_provident_fund"
                                )

                                SalaryInputField(
                                    label = strings.incomeTax,
                                    value = state.incomeTaxText,
                                    onValueChange = { viewModel.updateIncomeTax(it) },
                                    testTag = "input_income_tax"
                                )

                                SalaryInputField(
                                    label = strings.otherDeduction,
                                    value = state.otherDeductionText,
                                    onValueChange = { viewModel.updateOtherDeduction(it) },
                                    testTag = "input_other_deduction"
                                )

                                // Informational note on tax
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(10.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Info,
                                            contentDescription = "Tax Note",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier
                                                .size(16.dp)
                                                .padding(top = 2.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = strings.taxNote,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                // Custom added deductions
                                state.customDeductions.forEach { item ->
                                    CustomItemRow(
                                        item = item,
                                        isBangla = isBangla,
                                        onRemove = { viewModel.removeCustomDeduction(item.id) },
                                        testTagPrefix = "custom_deduction"
                                    )
                                }

                                // Add Deduction Button
                                TextButton(
                                    onClick = { viewModel.openAddCustomDeductionDialog() },
                                    modifier = Modifier.testTag("add_deduction_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = strings.addDeduction,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }
                        }

                        // Salary Breakdown Summary Card
                        SalaryBreakdownCard(
                            result = state.result,
                            strings = strings,
                            language = state.language
                        )

                        // Action Buttons: Reset & Share
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                onClick = { viewModel.showResetConfirmDialog() },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .testTag("main_reset_button"),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = strings.reset,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }

                            OutlinedButton(
                                onClick = {
                                    val summary = viewModel.generateShareSummary(strings)
                                    clipboardManager.setText(AnnotatedString(summary))
                                    Toast.makeText(context, strings.copiedToClipboard, Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .testTag("copy_summary_button"),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = strings.shareBreakdown,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }

    // Add Custom Allowance Dialog
    if (state.showAddCustomAllowanceDialog) {
        AddCustomItemDialog(
            title = strings.addCustomAllowanceTitle,
            strings = strings,
            onDismiss = { viewModel.closeAddCustomAllowanceDialog() },
            onConfirm = { name, amount -> viewModel.addCustomAllowance(name, amount) }
        )
    }

    // Add Custom Deduction Dialog
    if (state.showAddCustomDeductionDialog) {
        AddCustomItemDialog(
            title = strings.addCustomDeductionTitle,
            strings = strings,
            onDismiss = { viewModel.closeAddCustomDeductionDialog() },
            onConfirm = { name, amount -> viewModel.addCustomDeduction(name, amount) }
        )
    }

    // Reset Confirmation Dialog
    if (state.showResetConfirmDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissResetConfirmDialog() },
            title = {
                Text(
                    text = strings.resetConfirmTitle,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Text(
                    text = strings.resetConfirmMessage,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { viewModel.resetAll() },
                    modifier = Modifier.testTag("confirm_reset_button")
                ) {
                    Text(
                        text = strings.confirm,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { viewModel.dismissResetConfirmDialog() },
                    modifier = Modifier.testTag("cancel_reset_button")
                ) {
                    Text(text = strings.cancel)
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }
}

@Composable
private fun CustomItemRow(
    item: CustomSalaryItem,
    isBangla: Boolean,
    onRemove: () -> Unit,
    testTagPrefix: String
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("${testTagPrefix}_${item.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = CurrencyFormatter.formatCurrency(item.amount, isBangla),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(
                onClick = onRemove,
                modifier = Modifier
                    .size(32.dp)
                    .testTag("${testTagPrefix}_delete_${item.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove ${item.name}",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
