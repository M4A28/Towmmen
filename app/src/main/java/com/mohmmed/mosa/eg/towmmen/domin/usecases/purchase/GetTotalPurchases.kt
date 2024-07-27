package com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase

import com.mohmmed.mosa.eg.towmmen.data.repository.PurchaseRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalPurchases @Inject constructor(
    private val purchasesRepositoryImp: PurchaseRepositoryImp
){
    operator fun invoke(): Flow<Int> {
        return purchasesRepositoryImp.getTotalPurchases()
    }
}