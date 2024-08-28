package com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase

import com.mohmmed.mosa.eg.towmmen.data.module.Purchase
import com.mohmmed.mosa.eg.towmmen.data.module.PurchaseItem
import com.mohmmed.mosa.eg.towmmen.data.repository.PurchaseRepositoryImp
import javax.inject.Inject

class InsertFullPurchase @Inject constructor(
    private val purchasesRepositoryImp: PurchaseRepositoryImp
){
    suspend operator fun invoke(purchase: Purchase, items: List<PurchaseItem>) {
        purchasesRepositoryImp.insertFullPurchase(purchase, items)
    }
}
