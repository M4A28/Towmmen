package com.mohmmed.mosa.eg.towmmen.data.repository

import com.mohmmed.mosa.eg.towmmen.data.local.dao.DealerDao
import com.mohmmed.mosa.eg.towmmen.data.module.Dealer
import com.mohmmed.mosa.eg.towmmen.domin.repostory.DealerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DealerRepositoryImp @Inject constructor(
    private val dealerDao: DealerDao
) : DealerRepository {
    override suspend fun upsertDealer(dealer: Dealer) {
        dealerDao.upsertDealer(dealer)
    }

    override suspend fun deleteDealer(dealer: Dealer) {
        dealerDao.deleteDealer(dealer)
    }

    override fun getAllDealers(): Flow<List<Dealer>> {
        return dealerDao.getAllDealers()
    }

    override fun getDealerById(dealerId: Int): Flow<Dealer> {
        return dealerDao.getDealerById(dealerId)
    }

}