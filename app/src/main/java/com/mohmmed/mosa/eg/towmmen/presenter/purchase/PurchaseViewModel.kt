package com.mohmmed.mosa.eg.towmmen.presenter.purchase

import androidx.lifecycle.ViewModel
import com.mohmmed.mosa.eg.towmmen.domin.module.CustomerWithPurchases
import com.mohmmed.mosa.eg.towmmen.domin.module.Purchase
import com.mohmmed.mosa.eg.towmmen.domin.module.PurchaseWithDetails
import com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase.PurchaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PurchaseViewModel @Inject constructor(
        private val purchaseUseCases: PurchaseUseCases
    ): ViewModel() {

     fun getAllPurchases(): Flow<List<Purchase>> {
        return purchaseUseCases.getAllPurchases()
    }

     fun getCustomersWithPurchases(): Flow<List<CustomerWithPurchases>> {
        return purchaseUseCases.getCustomersWithPurchases()
    }

     fun getPurchasesWithDetails(): Flow<List<PurchaseWithDetails>> {
        return purchaseUseCases.getPurchasesWithDetails()
    }

     suspend fun insertPurchase(purchase: Purchase) {
        purchaseUseCases.insertPurchase(purchase)
    }

     suspend fun deletePurchase(purchase: Purchase) {
        purchaseUseCases.deletePurchase(purchase)
    }

     fun getPurchasesByCustomerId(customerId: Int): Flow<List<Purchase>> {
        return purchaseUseCases.getPurchasesByCustomerId(customerId)
    }

     fun getTotalPurchases(): Flow<Int> {
        return purchaseUseCases.getTotalPurchases()
    }

     fun getTotalPurchasesByCustomer(customerId: Int): Flow<Int> {
        return purchaseUseCases.getTotalPurchasesByCustomer(customerId)
    }

}