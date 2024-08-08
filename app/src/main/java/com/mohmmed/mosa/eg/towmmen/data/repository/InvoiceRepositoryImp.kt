package com.mohmmed.mosa.eg.towmmen.data.repository

import com.mohmmed.mosa.eg.towmmen.data.local.dao.InvoiceDao
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceByMonth
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceProfitByMonth
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceWithItems
import com.mohmmed.mosa.eg.towmmen.data.module.TopProduct
import com.mohmmed.mosa.eg.towmmen.domin.repostory.InvoiceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InvoiceRepositoryImp @Inject constructor(
 private val invoiceDao: InvoiceDao
): InvoiceRepository {
    override suspend fun upsertInvoice(invoice: Invoice) {
        invoiceDao.upsertInvoice(invoice)
    }

    override suspend fun upsertInvoiceItems(items: List<InvoiceItem>) {
         invoiceDao.upsertInvoiceItems(items)
    }

    override fun getInvoiceWithItems(invoiceId: String): Flow<InvoiceWithItems?> {
        return invoiceDao.getInvoiceWithItems(invoiceId)
    }

    override fun getAllInvoices(): Flow<List<Invoice>> {
        return invoiceDao.getAllInvoices()
    }

    override fun getInvoiceItems(invoiceId: String): Flow<List<InvoiceItem>> {
        return invoiceDao.getInvoiceItems(invoiceId)
    }

    override fun getAllInvoicesWithItems(): Flow<List<InvoiceWithItems>> {
        return invoiceDao.getAllInvoicesWithItems()
    }


    override suspend fun deleteInvoice(invoice: Invoice) {
        invoiceDao.deleteInvoice(invoice)
    }

    override suspend fun deleteInvoiceItem(item: InvoiceItem) {
        invoiceDao.deleteInvoiceItem(item)
    }

    override suspend fun deleteInvoiceById(invoiceId: String) {
        invoiceDao.deleteInvoiceById(invoiceId)
    }

    override suspend fun insertFullInvoice(invoice: Invoice, items: List<InvoiceItem>) {
        //val invoiceId = invoice.invoiceId
        //invoiceDao.upsertInvoice(invoice)
        //invoiceDao.upsertInvoiceItems(items)
        invoiceDao.insertFullInvoice(invoice, items)

    }



    override fun getAllInvoiceProfit(): Flow<Double?> {
        return invoiceDao.getAllInvoiceProfit()
    }

    override fun getInvoiceAvg(): Flow<Double?> {
        return invoiceDao.getInvoiceAvg()
    }

    override fun getMaxInvoice(): Flow<Invoice> {
        return invoiceDao.getMaxInvoice()
    }

    override fun getInvoicesWithItemsByCustomerId(customerId: Int): Flow<List<InvoiceWithItems>> {
        return invoiceDao.getInvoicesWithItemsByCustomerId(customerId)
    }

    override fun getTopSelling(): Flow<List<TopProduct>> {
        return invoiceDao.getTopSelling()
    }

    override fun getTopSellingCurrentMonth(): Flow<List<TopProduct>> {
        return invoiceDao.getTopSellingCurrentMonth()
    }

    override fun getTopSellingCurrentDay(): Flow<List<TopProduct>> {
        return invoiceDao.getTopSellingCurrentDay()
    }

    override fun getInvoiceProfitForCurrentMonth(): Flow<Double?> {
        return invoiceDao.getInvoiceProfitForCurrentMonth()
    }

    override fun getInvoiceProfitForCurrentDay(): Flow<Double?> {
        return invoiceDao.getInvoiceProfitForCurrentDay()
    }

    override fun getInvoiceProfitByMonth(): Flow<List<InvoiceProfitByMonth>> {
        return invoiceDao.getInvoiceProfitByMonth()
    }

    override fun getMinInvoice(): Flow<Invoice> {
        return invoiceDao.getMaxInvoice()
    }

    override fun getInvoiceCountByMonth(): Flow<List<InvoiceByMonth>> {
        return invoiceDao.getInvoiceCountByMonth()
    }

    override fun getInvoiceByCustomer(customerName: String): Flow<List<Invoice>> {
        return invoiceDao.getInvoiceByCustomer(customerName)
    }


}