package com.mohmmed.mosa.eg.towmmen.domin.usecases.dealer

import com.mohmmed.mosa.eg.towmmen.data.module.Dealer
import com.mohmmed.mosa.eg.towmmen.data.repository.DealerRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDealerById @Inject constructor(
    private val dealerRepository: DealerRepositoryImp
) {
    operator fun invoke(dealerId: Int): Flow<Dealer> {
        return dealerRepository.getDealerById(dealerId)
    }

}
