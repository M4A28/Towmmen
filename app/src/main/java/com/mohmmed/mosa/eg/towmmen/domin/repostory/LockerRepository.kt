package com.mohmmed.mosa.eg.towmmen.domin.repostory

import com.mohmmed.mosa.eg.towmmen.data.module.Locker
import kotlinx.coroutines.flow.Flow

interface LockerRepository {

    suspend fun upsertLockerTransaction(locker: Locker)
    suspend fun deleteLockerTransaction(locker: Locker)

    suspend fun clearLocker()
    fun getAllLockerTransactions(): Flow<List<Locker>>

    fun getSubLockerTransactions(): Flow<List<Locker>>

    fun getADDLockerTransactions(): Flow<List<Locker>>

}