package com.mohmmed.mosa.eg.towmmen.presenter.purchase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.data.module.Locker
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.data.module.Purchase
import com.mohmmed.mosa.eg.towmmen.data.module.PurchaseItem
import com.mohmmed.mosa.eg.towmmen.data.module.PurchaseWithItems
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker.LockerUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker_setting.LockerSettingUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.ProductUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase.PurchaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchaseViewModel
@Inject constructor(
    private val purchaseUseCases: PurchaseUseCases,
    private val productUseCase: ProductUseCases,
    private val lockerUseCases: LockerUseCases,
    private val lockerSettingUseCases: LockerSettingUseCases
    ): ViewModel() {

    val products = productUseCase.getALlProducts()


    val canSavePurchaseToDb: StateFlow<Boolean> = lockerSettingUseCases.getCanAddBuyToDb()
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    fun upsertLockerTransaction(locker: Locker){
        viewModelScope.launch(Dispatchers.IO) {
            lockerUseCases.upsertLocker(locker)
        }

    }
    fun updateProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
            productUseCase.updateProduct(product)
        }
    }

    fun insertPurchase(purchase: Purchase){
        viewModelScope.launch(Dispatchers.IO) {
            purchaseUseCases.insertPurchase(purchase)
        }
    }

    fun deletePurchase(purchase: Purchase){
        viewModelScope.launch(Dispatchers.IO) {
            purchaseUseCases.deletePurchase(purchase)
        }
    }


    fun clearPurchases(){
        viewModelScope.launch(Dispatchers.IO) {
            purchaseUseCases.clearPurchasesDate()
        }
    }

    fun getAllPurchases(): Flow<List<Purchase>> {
        return purchaseUseCases.getAllPurchases()
    }

    fun getAllPurchasesWithItemsByDealerId(id: Int): Flow<List<PurchaseWithItems>> {
        return purchaseUseCases.getPurchaseWithItemsByDealerId(id)
    }

    fun getAllPurchasesWithItems(): Flow<List<PurchaseWithItems>> {
        return purchaseUseCases.getPurchaseWithItems()
    }

    fun insertFullPurchases(purchase: Purchase, items: List<PurchaseItem> ){
        viewModelScope.launch(Dispatchers.IO) {
            purchaseUseCases.insertFullPurchase(purchase, items)
        }
    }

    fun upsertPurchases(items: List<PurchaseItem> ){
        viewModelScope.launch(Dispatchers.IO) {
            purchaseUseCases.upsertPurchaseWithItems(items)
        }
    }

    // to be contiun



}