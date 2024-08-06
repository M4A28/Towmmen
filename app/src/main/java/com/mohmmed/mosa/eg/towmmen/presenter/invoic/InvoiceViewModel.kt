package com.mohmmed.mosa.eg.towmmen.presenter.invoic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceWithItems
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.InvoiceUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.localuser.AppEntryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
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

/*    fun generateInvoiceNumber(currentDate: Date = Date()): String{
        val date = SimpleDateFormat("yyyyddmm").format(currentDate)
        var lastInvoiceNumber by mutableIntStateOf(0)
        localMangerUseCases.readLastInvoiceNumber().onEach {
            lastInvoiceNumber  = it
        }
        viewModelScope.launch {
            localMangerUseCases.saveLastInvoiceNumber(lastInvoiceNumber)
        }
        return String.format("INV-%s-%04d", date, lastInvoiceNumber )
    }*/

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