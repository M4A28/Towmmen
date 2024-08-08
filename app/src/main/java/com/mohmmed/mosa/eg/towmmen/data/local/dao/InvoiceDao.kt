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
import com.mohmmed.mosa.eg.towmmen.data.module.TopProduct
import kotlinx.coroutines.flow.Flow
import java.util.Date

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

    @Query("SELECT SUM((totalAmount)) FROM invoices  " +
            "WHERE strftime('%Y-%m', date/1000, 'unixepoch') = strftime('%Y-%m', 'now')")
    fun getInvoiceProfitForCurrentMonth(): Flow<Double?>

    @Query("SELECT SUM((totalAmount)) FROM invoices  " +
            "WHERE strftime('%Y-%m-%d', date/1000, 'unixepoch') = strftime('%Y-%m-%d', 'now')")
    fun getInvoiceProfitForCurrentDay(): Flow<Double?>

    @Query("SELECT AVG((totalAmount)) FROM invoices")
    fun getInvoiceAvg(): Flow<Double?>

    @Query("SELECT * FROM invoices  ORDER BY totalAmount DESC LIMIT 1 ")
    fun getMaxInvoice(): Flow<Invoice>

    @Query("SELECT * FROM invoices ORDER BY totalAmount ASC LIMIT 1")
    fun getMinInvoice(): Flow<Invoice>


    @Query("SELECT strftime('%Y-%m', date/1000, 'unixepoch') as month, COUNT(*) as count" +
            " FROM invoices GROUP BY month ORDER BY month")
    fun getInvoiceCountByMonth(): Flow<List<InvoiceByMonth>>

    @Query("SELECT strftime('%Y-%m', date/1000, 'unixepoch') as month, SUM(totalAmount) as profit" +
            " FROM invoices GROUP BY month ORDER BY month")
    fun getInvoiceProfitByMonth(): Flow<List<InvoiceProfitByMonth>>




    @Query("SELECT * FROM invoices WHERE customerName = :customerName ")
    fun getInvoiceByCustomer(customerName: String): Flow<List<Invoice>>


    @Query("SELECT * FROM invoices ORDER BY date DESC")
    fun getAllInvoices(): Flow<List<Invoice>>

    @Query("SELECT * FROM invoice_items WHERE invoiceId = :invoiceId")
    fun getInvoiceItems(invoiceId: String): Flow<List<InvoiceItem>>

    @Transaction
    @Query("SELECT * FROM invoices ORDER BY date DESC ")
    fun getAllInvoicesWithItems(): Flow<List<InvoiceWithItems>>

    @Transaction
    @Query("SELECT * FROM invoices WHERE customerId = :customerId ORDER BY date DESC ")
    fun getInvoicesWithItemsByCustomerId(customerId: Int): Flow<List<InvoiceWithItems>>


    @Query("SELECT productId, productName,  SUM(quantity) AS productQuantity" +
            " FROM invoice_items GROUP BY productName " +
            "ORDER BY productQuantity DESC LIMIT 5")
    fun getTopSelling(): Flow<List<TopProduct>>



    @Query("SELECT productId, productName, SUM(quantity) AS productQuantity " +
            " FROM invoice_items " +
            " WHERE strftime('%Y-%m', purchaseDate/1000, 'unixepoch') = strftime('%Y-%m', 'now') " +
            "GROUP BY productName " +
            "ORDER BY productQuantity DESC LIMIT 5")
    fun getTopSellingCurrentMonth(): Flow<List<TopProduct>>
    @Query("SELECT productId, productName, SUM(quantity) AS productQuantity " +
            " FROM invoice_items " +
            " WHERE strftime('%Y-%m-%d', purchaseDate/1000, 'unixepoch') = strftime('%Y-%m-%d', 'now') " +
            "GROUP BY productName " +
            "ORDER BY productQuantity DESC LIMIT 5")
    fun getTopSellingCurrentDay(): Flow<List<TopProduct>>

    // todo make viewmodel, repository, usecase
    @Query("SELECT productId, productName, SUM(quantity) AS productQuantity " +
            " FROM invoice_items " +
            " WHERE  purchaseDate BETWEEN strftime('%Y-%m-%d', :start/1000, 'unixepoch') " +
            "AND strftime('%Y-%m-%d', :end/1000, 'unixepoch') " +
            "GROUP BY productName " +
            "ORDER BY productQuantity DESC LIMIT 5")
    fun getTopSellingBetweenDates(start: Date, end: Date): Flow<List<TopProduct>>



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