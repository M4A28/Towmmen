package com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase

import com.mohmmed.mosa.eg.towmmen.data.repository.PurchaseRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalPurchasesByCustomer @Inject constructor(
    private val purchasesRepositoryImp: PurchaseRepositoryImp
){
    operator fun invoke(id: Int): Flow<Int> {
        return purchasesRepositoryImp.getTotalPurchasesByCustomer(id)
    }
}