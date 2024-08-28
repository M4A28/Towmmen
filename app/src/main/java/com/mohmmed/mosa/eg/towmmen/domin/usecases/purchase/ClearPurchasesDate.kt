package com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase

import com.mohmmed.mosa.eg.towmmen.data.repository.PurchaseRepositoryImp
import javax.inject.Inject

class ClearPurchasesDate @Inject constructor(
    private val purchasesRepositoryImp: PurchaseRepositoryImp
){
    suspend operator fun invoke() {
        purchasesRepositoryImp.clearPurchasesDate()
    }
}
