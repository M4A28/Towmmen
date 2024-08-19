package com.mohmmed.mosa.eg.towmmen.domin.usecases.dealer

import com.mohmmed.mosa.eg.towmmen.data.module.Dealer
import com.mohmmed.mosa.eg.towmmen.data.repository.DealerRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllDealers @Inject constructor(
    private val dealerRepository: DealerRepositoryImp
) {
    operator fun invoke(): Flow<List<Dealer>> {
        return dealerRepository.getAllDealers()
    }

}
