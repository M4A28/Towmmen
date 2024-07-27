package com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase

import com.mohmmed.mosa.eg.towmmen.data.repository.PurchaseRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.module.Purchase
import javax.inject.Inject

class InsertPurchase @Inject constructor(
    private val purchasesRepositoryImp: PurchaseRepositoryImp
){
    suspend operator fun invoke(purchase: Purchase) {
         purchasesRepositoryImp.insertPurchase(purchase)
    }
}
