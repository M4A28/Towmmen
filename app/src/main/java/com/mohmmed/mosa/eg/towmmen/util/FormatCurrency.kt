package com.mohmmed.mosa.eg.towmmen.util
import java.text.NumberFormat
import java.util.Locale

fun formatCurrency(amount: Double, locale: Locale = Locale.getDefault()): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance(locale)
    format.maximumFractionDigits = 2
    format.minimumFractionDigits = 2
    return format.format(amount)
}