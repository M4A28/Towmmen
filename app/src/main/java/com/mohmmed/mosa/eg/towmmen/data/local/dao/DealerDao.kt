package com.mohmmed.mosa.eg.towmmen.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mohmmed.mosa.eg.towmmen.data.module.Dealer
import kotlinx.coroutines.flow.Flow

@Dao
interface DealerDao {
    @Upsert
    suspend fun upsertDealer(dealer: Dealer)

    @Delete
    suspend fun deleteDealer(dealer: Dealer)

    @Query("SELECT * FROM dealers ORDER BY createDate DESC")
    fun getAllDealers(): Flow<List<Dealer>>


    @Query("SELECT * FROM dealers WHERE dealerId = :dealerId")
    fun getDealerById(dealerId: Int): Flow<Dealer>

    @Query("DELETE FROM dealers")
    suspend fun clearDealersDate()
    // todo add some statist like most purchaers from dealer

}