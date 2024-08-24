package com.mohmmed.mosa.eg.towmmen.domin.repostory

import com.mohmmed.mosa.eg.towmmen.data.module.Dealer
import kotlinx.coroutines.flow.Flow

interface DealerRepository {

    suspend fun upsertDealer(dealer: Dealer)

    suspend fun deleteDealer(dealer: Dealer)

    fun getAllDealers(): Flow<List<Dealer>>

    fun getDealerById(dealerId: Int): Flow<Dealer>
    suspend fun clearDealersDate()


}