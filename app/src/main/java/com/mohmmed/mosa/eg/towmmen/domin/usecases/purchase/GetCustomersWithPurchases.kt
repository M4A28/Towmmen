package com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase

import com.mohmmed.mosa.eg.towmmen.data.repository.PurchaseRepositoryImp
import com.mohmmed.mosa.eg.towmmen.data.module.CustomerWithPurchases
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCustomersWithPurchases @Inject constructor(
    private val purchasesRepositoryImp: PurchaseRepositoryImp
){
    operator fun invoke(): Flow<List<CustomerWithPurchases>> {
        return purchasesRepositoryImp.getCustomersWithPurchases()
    }
}