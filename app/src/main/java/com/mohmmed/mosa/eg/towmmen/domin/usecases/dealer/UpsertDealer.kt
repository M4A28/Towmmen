package com.mohmmed.mosa.eg.towmmen.domin.usecases.dealer

import com.mohmmed.mosa.eg.towmmen.data.module.Dealer
import com.mohmmed.mosa.eg.towmmen.data.repository.DealerRepositoryImp
import javax.inject.Inject

class UpsertDealer @Inject constructor(
    private val dealerRepository: DealerRepositoryImp
) {
    suspend  operator fun invoke(dealer: Dealer){
        dealerRepository.upsertDealer(dealer)
    }

}
