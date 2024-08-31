package com.mohmmed.mosa.eg.towmmen.presenter.invoic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceByPeriod
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceProfitByPeriod
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceWithItems
import com.mohmmed.mosa.eg.towmmen.data.module.Locker
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.data.module.TopProduct
import com.mohmmed.mosa.eg.towmmen.domin.usecases.customer.CustomerUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.InvoiceUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker.LockerUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker_setting.LockerSettingUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InvoiceViewModel @Inject constructor(
    private val invoiceUseCases: InvoiceUseCases,
    private val lockerSetting: LockerSettingUseCases,
    private val lockerUseCases: LockerUseCases,
    private val productUseCase: ProductUseCases,
    private val customerUseCases: CustomerUseCases
): ViewModel() {

    val products = productUseCase.getALlProducts()
    fun updateProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
            productUseCase.updateProduct(product)
        }
    }
    fun upsertInvoice(invoice: Invoice){
        viewModelScope.launch {
            invoiceUseCases.upsertInvoice(invoice)
        }
    }

    fun updateCustomer(customer: Customer){
        viewModelScope.launch(Dispatchers.IO){
            customerUseCases.updateCustomer(customer)
        }
    }

    val canSaveSellToDb: StateFlow<Boolean> = lockerSetting.getCanAddSellToDb()
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    fun upsertLockerTransaction(locker: Locker){
        viewModelScope.launch(Dispatchers.IO) {
            lockerUseCases.upsertLocker(locker)
        }

    }




    fun getTotalInvoices():Flow<Double?>{
        return invoiceUseCases.getTotalInvoices()
    }

    fun getMinInvoice(): Flow<Invoice>{
        return invoiceUseCases.getMinInvoice()
    }
    fun getMaxInvoice(): Flow<Invoice>{
        return invoiceUseCases.getMaxInvoice()
    }

    fun getInvoiceProfitByMonth(): Flow<List<InvoiceProfitByPeriod>> {
        return invoiceUseCases.getInvoiceProfitByMonth()
    }

    fun getInvoiceAvg(): Flow<Double?> {
        return invoiceUseCases.getInvoiceAvg()
    }

    fun getInvoiceCountByMonth(): Flow<List<InvoiceByPeriod>> {
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


    val avgInvoicePerMonth : StateFlow<Double?> = invoiceUseCases
        .getAvgInvoicePerMonth()
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)


    fun clearInvoiceData(){
        viewModelScope.launch(Dispatchers.IO) {
            invoiceUseCases.clearInvoiceData()
        }
    }



}