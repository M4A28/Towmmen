package com.mohmmed.mosa.eg.towmmen.data.module

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity(tableName = "invoices")
data class Invoice(
    @PrimaryKey(autoGenerate = false)
    val invoiceId: String,
    val customerId: Int,
    val customerName: String,
    val date: Date = Date(),
    val profit: Double,
    val totalAmount: Double
)



@Entity(tableName = "invoice_items",
    foreignKeys = [ForeignKey(
        entity = Invoice::class,
        parentColumns = ["invoiceId"],
        childColumns = ["invoiceId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("invoiceId")]
)
data class InvoiceItem(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,
    val invoiceId: String,
    val productId: Int,
    val productName: String,
    var quantity: Int,
    val unitPrice: Double,
    val unitCost: Double,
    val purchaseDate: Date
)

data class InvoiceWithItems(
    @Embedded val invoice: Invoice,
    @Relation(
        parentColumn = "invoiceId",
        entityColumn = "invoiceId"
    )
    val items: List<InvoiceItem>
)



data class InvoiceByPeriod(val period: String, val count: Int)
data class InvoiceProfitByPeriod(val period: String, val profit: Double)

