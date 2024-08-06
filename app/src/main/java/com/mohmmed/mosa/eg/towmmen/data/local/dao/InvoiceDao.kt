package com.mohmmed.mosa.eg.towmmen.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceByMonth
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceProfitByMonth
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceDao {

    @Upsert
    suspend fun upsertInvoice(invoice: Invoice)

    @Upsert
    suspend fun upsertInvoiceItems(items: List<InvoiceItem>)

    @Transaction
    @Query("SELECT * FROM invoices WHERE invoiceId = :invoiceId")
    fun getInvoiceWithItems(invoiceId: String): Flow<InvoiceWithItems?>


    @Query("SELECT SUM((totalAmount)) FROM invoices")
    fun getAllInvoiceProfit(): Flow<Double?>

    @Query("SELECT AVG((totalAmount)) FROM invoices")
    fun getInvoiceAvg(): Flow<Double?>

    @Query("SELECT MAX((totalAmount)) FROM invoices")
    fun getMaxInvoice(): Flow<Invoice>

    @Query("SELECT MIN((totalAmount)) FROM invoices")
    fun getMinInvoice(): Flow<Invoice>


    @Query("SELECT strftime('%Y-%m', date) as month, COUNT(*)" +
            " FROM invoices GROUP BY month ORDER BY month")
    fun getInvoiceCountByMonth(): Flow<List<InvoiceByMonth>>

    @Query("SELECT strftime('%Y-%m', date) as month, SUM(totalAmount)" +
            " FROM invoices GROUP BY month ORDER BY month")
    fun getInvoiceProfitByMonth(): Flow<List<InvoiceProfitByMonth>>




    @Query("SELECT * FROM invoices WHERE customerName = :customerName ")
    fun getInvoiceByCustomer(customerName: String): Flow<List<Invoice>>


    @Query("SELECT * FROM invoices ORDER BY date DESC")
    fun getAllInvoices(): Flow<List<Invoice>>

    @Query("SELECT * FROM invoice_items WHERE invoiceId = :invoiceId")
    fun getInvoiceItems(invoiceId: String): Flow<List<InvoiceItem>>

    @Transaction
    @Query("SELECT * FROM invoices")
    fun getAllInvoicesWithItems(): Flow<List<InvoiceWithItems>>


    @Delete
    suspend fun deleteInvoice(invoice: Invoice)

    @Delete
    suspend fun deleteInvoiceItem(item: InvoiceItem)

    @Query("DELETE FROM invoices WHERE invoiceId = :invoiceId")
    suspend fun deleteInvoiceById(invoiceId: String)

    @Transaction
    suspend fun insertFullInvoice(invoice: Invoice, items: List<InvoiceItem>) {
        upsertInvoice(invoice)
        val invoiceId  = invoice.invoiceId
        items.forEach { item ->
            upsertInvoiceItems(listOf(item.copy(invoiceId = invoiceId)))
        }
    }

}