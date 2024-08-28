package com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.data.module.Category
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.data.module.Dealer
import com.mohmmed.mosa.eg.towmmen.data.module.Expanse
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.Locker
import com.mohmmed.mosa.eg.towmmen.data.module.Note
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.domin.usecases.app_theme.AppThemeUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.category.CategoryUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.customer.CustomerUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.dealer.DealerUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse.ExpanseUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice.InvoiceUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker.LockerUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker_setting.LockerSettingUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.note.NoteUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.ProductUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase.PurchaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val customerUseCases: CustomerUseCases,
    private val expanseUseCases: ExpanseUseCases,
    private val invoiceUseCases: InvoiceUseCases,
    private val appThemeUseCases: AppThemeUseCases,
    private val noteUseCases: NoteUseCases,
    private val dealersUseCases: DealerUseCases,
    private val categoryUseCases: CategoryUseCases,
    private val lockerUseCases: LockerUseCases,
    private val lockerSettingUseCases: LockerSettingUseCases,
    private val purchaseUseCases: PurchaseUseCases
    ): ViewModel(){


    val invoices: StateFlow<List<Invoice>> =
        invoiceUseCases.getAllInvoice()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val lockers: StateFlow<List<Locker>> =
        lockerUseCases.getAllLocker()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val notes: StateFlow<List<Note>> =
        noteUseCases.getAllNote()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    val products: StateFlow<List<Product>> =
        productUseCases.getALlProducts()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val customers: StateFlow<List<Customer>>
        get() = customerUseCases.getAllCustomers()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val dealers: StateFlow<List<Dealer>> =
        dealersUseCases.getAllDealers()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val categorys: StateFlow<List<Category>> =
        categoryUseCases.getAllCategory()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val expanses: StateFlow<List<Expanse>> =
        expanseUseCases.getAllExpanse()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    val theme: StateFlow<AppTheme> = appThemeUseCases.getAppTheme()
        .stateIn(viewModelScope, SharingStarted.Eagerly, AppTheme.SYSTEM)

    fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            appThemeUseCases.setAppTheme(theme)
        }
    }

    val canSaveSellToDb: StateFlow<Boolean> = lockerSettingUseCases.getCanAddSellToDb()
    .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    fun setCanSaveSellToDb(canSave: Boolean){
        viewModelScope.launch {
            lockerSettingUseCases.setCanSaveExpanseToDb(canSave)
        }
    }


    val canSaveBuyToDb: StateFlow<Boolean> = lockerSettingUseCases.getCanAddBuyToDb()
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    fun setCanSaveBuyToDb(canSave: Boolean){
        viewModelScope.launch {
            lockerSettingUseCases.setCanAddBuyToDb(canSave)
        }
    }

    val canSaveExpanseToDb: StateFlow<Boolean> = lockerSettingUseCases.getCanSaveExpanseToDb()
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    fun setCanSaveExpanseToDb(canSave: Boolean){
        viewModelScope.launch {
            lockerSettingUseCases.setCanSaveExpanseToDb(canSave)
        }
    }





    fun clearInvoice(){
        viewModelScope.launch(Dispatchers.IO) {
            invoiceUseCases.clearInvoiceData()
        }
    }
    fun clearPurchase(){
        viewModelScope.launch(Dispatchers.IO) {
            purchaseUseCases.clearPurchasesDate()
        }
    }

    fun clearNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            noteUseCases.clearNoteDate()
        }
    }

    fun clearLocker(){
        viewModelScope.launch(Dispatchers.IO) {
            lockerUseCases.clearLocker()
        }
    }

    fun clearDealers(){
        viewModelScope.launch(Dispatchers.IO) {
            dealersUseCases.clearDealerData()
        }
    }

    fun clearCategorys(){
        viewModelScope.launch(Dispatchers.IO) {
            categoryUseCases.clearCategory()
        }
    }

    fun clearExpanses(){
        viewModelScope.launch(Dispatchers.IO) {
            expanseUseCases.clearExpanseData()
        }
    }

    fun clearProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            productUseCases.clearProductData()
        }
    }

    fun clearCustomer(){
        viewModelScope.launch(Dispatchers.IO) {
            customerUseCases.cleatCustomerData()
        }
    }




}