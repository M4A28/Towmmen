package com.mohmmed.mosa.eg.towmmen.data.module

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "invoices")
class Invoice(
    @PrimaryKey(autoGenerate = true)
    val invoiceId: Int = 0,
    val customerId: String,
    val customerName: String,
    val date: Date = Date(),
    val items: List<InvoiceItem>,
    val totalAmount: Double = items.sumOf { it.quantity * it.unitPrice }
) {
}
@Entity(tableName = "invoice_items")
data class InvoiceItem(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,
    val productId: String,
    val productName: String,
    val quantity: Int,
    val unitPrice: Double
)
