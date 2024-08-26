package com.mohmmed.mosa.eg.towmmen.presenter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.data.module.ExpansePerMonth
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceProfitByMonth
import com.mohmmed.mosa.eg.towmmen.data.module.MostTopProduct
import com.mohmmed.mosa.eg.towmmen.data.module.TopProduct
import com.mohmmed.mosa.eg.towmmen.domin.usecases.customer.CustomerUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse.ExpanseUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.InvoiceUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val customerUseCases: CustomerUseCases,
    private val expanseUseCases: ExpanseUseCases,
    private val invoiceUseCases: InvoiceUseCases,

): ViewModel(){

    val totalInvoices : StateFlow<Double?> =
        invoiceUseCases.getTotalInvoices()
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    val avgInvoicePerMonth : StateFlow<Double?> = invoiceUseCases
        .getAvgInvoicePerMonth()
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)


    val invoicePerMonth: StateFlow<List<InvoiceProfitByMonth>> =
        invoiceUseCases.getInvoiceProfitByMonth()
            .stateIn(viewModelScope, SharingStarted.Eagerly, listOf(InvoiceProfitByMonth("", 0.0)))

    val productCount:  StateFlow<Int?> =
        productUseCases
            .getProductsCount()
            .stateIn(viewModelScope, SharingStarted.Eagerly, 0)


    val productsCost: StateFlow<Double?> =
        productUseCases.totalProductsCost()
            .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)


    val avgExpansePerMonth: StateFlow<Double?> = expanseUseCases
        .getAvgExpansePerMonth()
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)


    val customerCount: StateFlow<Int?> =
        customerUseCases.getCustomerCount()
            .stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    val topSellingProducts: StateFlow<List<TopProduct>> =
        invoiceUseCases.getTopSelling()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val mostTopSellingProduct: StateFlow<List<MostTopProduct>> =
        invoiceUseCases.getMostTopProduct()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val expansePerMonthItems: StateFlow<List<ExpansePerMonth>> =
        expanseUseCases.getExpansePerMonth()
            .stateIn(viewModelScope, SharingStarted.Eagerly, listOf(ExpansePerMonth("", 0.0)))

    val invoices: StateFlow<List<Invoice>> =
        invoiceUseCases.getAllInvoice()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


}