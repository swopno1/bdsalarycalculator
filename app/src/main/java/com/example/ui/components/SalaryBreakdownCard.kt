package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.SalaryResult
import com.example.utils.AppLanguage
import com.example.utils.AppStrings
import com.example.utils.CurrencyFormatter

@Composable
fun SalaryBreakdownCard(
    result: SalaryResult,
    strings: AppStrings,
    language: AppLanguage,
    modifier: Modifier = Modifier
) {
    if (result.grossSalary <= 0.0) return

    val isBangla = language == AppLanguage.BANGLA
    val formattedBasic = CurrencyFormatter.formatCurrency(result.basicSalary, isBangla)
    val formattedAllowances = CurrencyFormatter.formatCurrency(result.totalAllowances, isBangla)
    val formattedGross = CurrencyFormatter.formatCurrency(result.grossSalary, isBangla)
    val formattedDeductions = CurrencyFormatter.formatCurrency(result.totalDeductions, isBangla)
    val formattedTakeHome = CurrencyFormatter.formatCurrency(result.takeHomeSalary, isBangla)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("salary_breakdown_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = strings.salaryBreakdown,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Proportional salary composition bar
            val basicFraction = (result.basicSalary / result.grossSalary).toFloat().coerceIn(0f, 1f)
            val allowancesFraction = (result.totalAllowances / result.grossSalary).toFloat().coerceIn(0f, 1f)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                if (basicFraction > 0f) {
                    Box(
                        modifier = Modifier
                            .weight(basicFraction)
                            .height(8.dp)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
                if (allowancesFraction > 0f) {
                    Box(
                        modifier = Modifier
                            .weight(allowancesFraction)
                            .height(8.dp)
                            .background(MaterialTheme.colorScheme.tertiary)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Breakdown rows
            BreakdownRow(
                label = strings.basicSalary,
                amount = formattedBasic,
                dotColor = MaterialTheme.colorScheme.primary
            )

            if (result.totalAllowances > 0.0) {
                Spacer(modifier = Modifier.height(6.dp))
                BreakdownRow(
                    label = strings.allowancesSection,
                    amount = "+ $formattedAllowances",
                    dotColor = MaterialTheme.colorScheme.tertiary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(8.dp))

            BreakdownRow(
                label = strings.grossSalary,
                amount = formattedGross,
                isBold = true
            )

            if (result.totalDeductions > 0.0) {
                Spacer(modifier = Modifier.height(6.dp))
                BreakdownRow(
                    label = strings.totalDeductions,
                    amount = "- $formattedDeductions",
                    amountColor = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(8.dp))

            BreakdownRow(
                label = strings.takeHomeSalary,
                amount = formattedTakeHome,
                isBold = true,
                amountColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun BreakdownRow(
    label: String,
    amount: String,
    modifier: Modifier = Modifier,
    isBold: Boolean = false,
    amountColor: Color = MaterialTheme.colorScheme.onSurface,
    dotColor: Color? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (dotColor != null) {
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(dotColor)
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }
            Text(
                text = label,
                style = if (isBold) MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                else MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Text(
            text = amount,
            style = if (isBold) MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            else MaterialTheme.typography.bodyMedium,
            color = amountColor
        )
    }
}
