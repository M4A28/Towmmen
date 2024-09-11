package com.mohmmed.mosa.eg.towmmen.presenter.debt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.data.module.Debt
import com.mohmmed.mosa.eg.towmmen.data.module.DebtItem
import com.mohmmed.mosa.eg.towmmen.data.module.DebtWithItem
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.domin.usecases.debt.DebtUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.InvoiceUseCases
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
class DebtViewModel @Inject constructor(
    private val debtUseCases: DebtUseCases,
    private val productUseCase: ProductUseCases,
    private val invoiceUseCases: InvoiceUseCases
): ViewModel() {

    val allDebtWithItem : StateFlow<List<DebtWithItem>> =
        debtUseCases.getAllDebtWithItems().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val notPaidDebtWithItem : StateFlow<List<DebtWithItem>> =
        debtUseCases.getNotPaidDebtWithItems().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val paidDebtWithItem : StateFlow<List<DebtWithItem>> =
        debtUseCases.getPaidDebtWithItems().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val totalDebt : StateFlow<Double?> =
        debtUseCases.getTotalDebt().stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    val products : StateFlow<List<Product>> =
        productUseCase.getALlProducts().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun getDebtByCustomerId(id: Int): Flow<List<DebtWithItem>> {
        return debtUseCases.getDebtByCustomerId(id)
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    }
    fun upsertDebt(debt: Debt){
        viewModelScope.launch(Dispatchers.IO) {
            debtUseCases.upsertDebt(debt)
        }
    }

    fun insertFullInvoice(invoice: Invoice, items: List<InvoiceItem>){
        viewModelScope.launch(Dispatchers.IO) {
            invoiceUseCases.insertFullInvoice(invoice, items)
        }
    }
    fun updateProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
            productUseCase.updateProduct(product)
        }
    }

    fun upsertDebtItem(debtItem: List<DebtItem>){
        viewModelScope.launch(Dispatchers.IO) {
            debtUseCases.upsertDebtItems(debtItem)
        }
    }

    fun deleteDebtItem(debtItem: List<DebtItem>){
        viewModelScope.launch(Dispatchers.IO) {
            debtUseCases.deleteDebtItems(debtItem)
        }
    }


    fun deleteDebt(debt: Debt){
        viewModelScope.launch(Dispatchers.IO) {
            debtUseCases.deleteDebt(debt)
        }
    }

    fun insertFullDebt(debt: Debt, debtItems: List<DebtItem>){
        viewModelScope.launch(Dispatchers.IO) {
            debtUseCases.insertFullDebt(debt, debtItems)
        }
    }
}