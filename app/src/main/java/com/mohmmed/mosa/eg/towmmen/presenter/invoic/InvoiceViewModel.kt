package com.mohmmed.mosa.eg.towmmen.presenter.invoic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceByMonth
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceProfitByMonth
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceWithItems
import com.mohmmed.mosa.eg.towmmen.data.module.TopProduct
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.InvoiceUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.localuser.AppEntryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InvoiceViewModel @Inject constructor(
    private val invoiceUseCases: InvoiceUseCases,
    private val localMangerUseCases: AppEntryUseCases
): ViewModel() {

    suspend fun upsertInvoice(invoice: Invoice){
        viewModelScope.launch {
            invoiceUseCases.upsertInvoice(invoice)
        }
    }


    fun getMinInvoice(): Flow<Invoice>{
        return invoiceUseCases.getMinInvoice()
    }
    fun getMaxInvoice(): Flow<Invoice>{
        return invoiceUseCases.getMaxInvoice()
    }

    fun getInvoiceProfitByMonth(): Flow<List<InvoiceProfitByMonth>> {
        return invoiceUseCases.getInvoiceProfitByMonth()
    }

    fun getInvoiceAvg(): Flow<Double?> {
        return invoiceUseCases.getInvoiceAvg()
    }

    fun getInvoiceCountByMonth(): Flow<List<InvoiceByMonth>> {
        return invoiceUseCases.getInvoiceCountByMonth()
    }

    fun getAllInvoiceProfit(): Flow<Double?> {
        return invoiceUseCases.getAllInvoiceProfit()
    }
    fun getInvoicesWithItemsByCustomerId(customerId: Int): Flow<List<InvoiceWithItems>>{
    return invoiceUseCases.getInvoicesWithItemsByCustomerId(customerId)
    }

    fun getInvoiceByCustomer(customerName: String): Flow<List<Invoice>> {
        return invoiceUseCases.getInvoiceByCustomer(customerName)
    }

    suspend fun upsertInvoiceItems(items: List<InvoiceItem>){
        viewModelScope.launch {
            invoiceUseCases.upsertInvoiceItems(items)
        }
    }

    fun getAllInvoices(): Flow<List<Invoice>>{
        return invoiceUseCases.getAllInvoice()
    }

    fun getInvoiceItems(invoiceId: String): Flow<List<InvoiceItem>>{
        return invoiceUseCases.getInvoiceItems(invoiceId)
    }

    fun getAllInvoicesWithItems(): Flow<List<InvoiceWithItems>>{
        return invoiceUseCases.getAllInvoicesWithItems()
    }
    fun getTopSellingItem(): Flow<List<TopProduct>>{
        return invoiceUseCases.getTopSelling()
    }
    fun getTopSellingItemCurrentMonth(): Flow<List<TopProduct>>{
        return invoiceUseCases.getTopSellingCurrentMonth()
    }

    fun getTopSellingItemCurrentDay(): Flow<List<TopProduct>>{
        return invoiceUseCases.getTopSellingCurrentDay()
    }


    fun deleteInvoice(invoice: Invoice){
        viewModelScope.launch {
            invoiceUseCases.deleteInvoice(invoice)
        }
    }

    fun deleteInvoiceItem(item: InvoiceItem){
        viewModelScope.launch {
            invoiceUseCases.deleteInvoiceItem(item)
        }
    }

    fun insertFullInvoice(invoice: Invoice, items: List<InvoiceItem>){
        viewModelScope.launch {
            invoiceUseCases.insertFullInvoice(invoice, items )
        }
    }



}