package com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase

import com.mohmmed.mosa.eg.towmmen.data.module.PurchaseItem
import com.mohmmed.mosa.eg.towmmen.data.repository.PurchaseRepositoryImp
import javax.inject.Inject

class UpsertPurchaseWithItems @Inject constructor(
    private val purchasesRepositoryImp: PurchaseRepositoryImp
){
    suspend operator fun invoke(items: List<PurchaseItem>) {
        purchasesRepositoryImp.upsertPurchaseWithItems(items)
    }
}
