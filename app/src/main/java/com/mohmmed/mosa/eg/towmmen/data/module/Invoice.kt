package com.mohmmed.mosa.eg.towmmen.data.module

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity(tableName = "invoices")
class Invoice(
    @PrimaryKey(autoGenerate = false)
    val invoiceId: String,
    val customerId: Int,
    val customerName: String,
    val date: Date = Date(),
    val totalAmount: Double
)



@Entity(tableName = "invoice_items",
    foreignKeys = [ForeignKey(
        entity = Invoice::class,
        parentColumns = ["invoiceId"],
        childColumns = ["invoiceId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class InvoiceItem(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,
    val invoiceId: String,
    val productId: Int,
    val productName: String,
    var quantity: Int,
    val unitPrice: Double
)

data class InvoiceWithItems(
    @Embedded val invoice: Invoice,
    @Relation(
        parentColumn = "invoiceId",
        entityColumn = "invoiceId"
    )
    val items: List<InvoiceItem>
)



data class InvoiceByMonth(val month: String, val count: Int)
data class InvoiceProfitByMonth(val month: String, val profit: Double)

