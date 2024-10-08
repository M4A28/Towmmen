package com.mohmmed.mosa.eg.towmmen.domin.repostory

import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceByPeriod
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceProfitByPeriod
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceWithItems
import com.mohmmed.mosa.eg.towmmen.data.module.MostTopProduct
import com.mohmmed.mosa.eg.towmmen.data.module.TopProduct
import kotlinx.coroutines.flow.Flow

interface InvoiceRepository {
    suspend fun upsertInvoice(invoice: Invoice)
    fun getInvoiceById(invoiceId: String):Flow<Invoice>


    suspend fun upsertInvoiceItems(items: List<InvoiceItem>)

    fun getInvoiceWithItems(invoiceId: String): Flow<InvoiceWithItems?>

    fun getAllInvoices(): Flow<List<Invoice>>

    fun getInvoiceItems(invoiceId: String): Flow<List<InvoiceItem>>

    fun getTotalInvoices(): Flow<Double?>


    fun getAllInvoicesWithItems(): Flow<List<InvoiceWithItems>>


    suspend fun deleteInvoice(invoice: Invoice)

    suspend fun deleteInvoiceItem(item: InvoiceItem)

    suspend fun deleteInvoiceById(invoiceId: String)

    suspend fun insertFullInvoice(invoice: Invoice, items: List<InvoiceItem>)


    fun getAllInvoiceProfit(): Flow<Double?>

    fun getInvoiceAvg(): Flow<Double?>

    fun getMaxInvoice(): Flow<Invoice>

    fun getInvoicesWithItemsByCustomerId(customerId: Int): Flow<List<InvoiceWithItems>>

    fun getTopSelling(): Flow<List<TopProduct>>

    fun getTopSellingCurrentMonth(): Flow<List<TopProduct>>

    fun getTopSellingCurrentDay(): Flow<List<TopProduct>>

    fun getInvoiceProfitForCurrentMonth(): Flow<Double?>

    fun getInvoiceProfitForCurrentDay(): Flow<Double?>

    fun getInvoiceProfitByMonth(): Flow<List<InvoiceProfitByPeriod>>

    fun getMinInvoice(): Flow<Invoice>

    fun getInvoiceCountByMonth(): Flow<List<InvoiceByPeriod>>

    fun getInvoiceByCustomer(customerName: String): Flow<List<Invoice>>

    fun getAvgInvoicePerMonth(): Flow<Double?>
    suspend fun clearInvoiceDate()

    fun getMostTopProduct(): Flow<List<MostTopProduct>>

    fun getInvoiceProfitByDay(): Flow<List<InvoiceProfitByPeriod>>

    fun getInvoiceProfitByWeek(): Flow<List<InvoiceProfitByPeriod>>


}