package com.mohmmed.mosa.eg.towmmen.util
import java.text.NumberFormat
import java.util.Currency

fun formatCurrency(amount: Double,
                   currencyCode: String = "EGP"): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 2
    format.minimumFractionDigits = 2
    format.currency = Currency.getInstance(currencyCode)
    return format.format(amount)
}