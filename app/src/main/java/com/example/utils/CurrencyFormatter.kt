package com.example.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

object CurrencyFormatter {

    const val CURRENCY_SYMBOL = "৳"

    /**
     * Formats a numeric amount using the South Asian numbering system:
     * e.g., 500 -> 500, 1000 -> 1,000, 50000 -> 50,000, 125000 -> 1,25,000, 10000000 -> 1,00,00,000
     */
    fun formatSouthAsian(amount: Double): String {
        if (amount.isNaN() || amount.isInfinite()) return "0"
        val rounded = Math.round(amount * 100.0) / 100.0
        val longPart = rounded.toLong()
        val fracPart = Math.round((rounded - longPart) * 100).toInt()

        val baseStr = longPart.toString()
        val formattedInt = if (baseStr.length <= 3) {
            baseStr
        } else {
            val lastThree = baseStr.takeLast(3)
            val remaining = baseStr.dropLast(3)
            val chunks = mutableListOf<String>()
            var i = remaining.length
            while (i > 0) {
                val start = maxOf(0, i - 2)
                chunks.add(0, remaining.substring(start, i))
                i = start
            }
            chunks.joinToString(",") + "," + lastThree
        }

        return if (fracPart > 0) {
            val fracStr = if (fracPart < 10) "0$fracPart" else "$fracPart"
            "$formattedInt.$fracStr"
        } else {
            formattedInt
        }
    }

    /**
     * Converts ASCII digits (0-9) to Bengali digits (০-৯).
     */
    fun toBengaliDigits(input: String): String {
        val enToBn = mapOf(
            '0' to '০', '1' to '১', '2' to '২', '3' to '৩', '4' to '৪',
            '5' to '৫', '6' to '৬', '7' to '৭', '8' to '৮', '9' to '৯'
        )
        return input.map { enToBn[it] ?: it }.joinToString("")
    }

    /**
     * Converts Bengali digits (০-৯) to ASCII digits (0-9).
     */
    fun toEnglishDigits(input: String): String {
        val bnToEn = mapOf(
            '০' to '0', '১' to '1', '২' to '2', '৩' to '3', '৪' to '4',
            '৫' to '5', '৬' to '6', '৭' to '7', '৮' to '8', '৯' to '9'
        )
        return input.map { bnToEn[it] ?: it }.joinToString("")
    }

    /**
     * Formats an amount with optional currency symbol and Bengali numeral support.
     */
    fun formatCurrency(
        amount: Double,
        isBangla: Boolean = false,
        withSymbol: Boolean = true
    ): String {
        val formatted = formatSouthAsian(amount)
        val numText = if (isBangla) toBengaliDigits(formatted) else formatted
        return if (withSymbol) "$CURRENCY_SYMBOL$numText" else numText
    }

    /**
     * Safely parses user input (English or Bengali digits, optional commas, decimals) into Double.
     */
    fun parseToDouble(raw: String): Double {
        if (raw.isBlank()) return 0.0
        val sanitized = toEnglishDigits(raw)
            .replace(",", "")
            .replace(CURRENCY_SYMBOL, "")
            .replace("BDT", "", ignoreCase = true)
            .replace(" ", "")
            .trim()

        return sanitized.toDoubleOrNull() ?: 0.0
    }

    /**
     * Sanitizes input while typing so only valid digits (or one decimal) are allowed.
     */
    fun sanitizeTypingInput(raw: String): String {
        val inEnglish = toEnglishDigits(raw)
            .replace(",", "")
            .replace(" ", "")
            .replace(CURRENCY_SYMBOL, "")

        // Filter valid characters: digits and single decimal dot
        val sb = StringBuilder()
        var hasDot = false
        for (c in inEnglish) {
            if (c.isDigit()) {
                sb.append(c)
            } else if (c == '.' && !hasDot) {
                hasDot = true
                sb.append(c)
            }
        }
        return sb.toString()
    }
}
