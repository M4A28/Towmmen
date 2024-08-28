package com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase

import com.mohmmed.mosa.eg.towmmen.data.module.PurchaseWithItems
import com.mohmmed.mosa.eg.towmmen.data.repository.PurchaseRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPurchaseWithItemsByDealerId @Inject constructor(
    private val purchasesRepositoryImp: PurchaseRepositoryImp
){
    operator fun invoke(dealerId: Int): Flow<List<PurchaseWithItems>> {
        return purchasesRepositoryImp.getPurchaseWithItemsByDealerId(dealerId)
    }
}
