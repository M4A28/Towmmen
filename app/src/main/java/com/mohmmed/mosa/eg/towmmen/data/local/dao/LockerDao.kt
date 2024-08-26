package com.mohmmed.mosa.eg.towmmen.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mohmmed.mosa.eg.towmmen.data.module.Locker
import kotlinx.coroutines.flow.Flow


@Dao
interface LockerDao {

    @Upsert
    suspend fun upsertLockerTransaction(locker: Locker)
    @Delete
    suspend fun deleteLockerTransaction(locker: Locker)

    @Query("DELETE FROM locker")
    suspend fun clearLocker()

    @Query("SELECT * FROM locker ORDER BY transActionDate DESC ")
    fun getAllLockerTransactions(): Flow<List<Locker>>

    @Query("SELECT * FROM locker WHERE transActionType = 'SUB' ")
    fun getSubLockerTransactions(): Flow<List<Locker>>

    @Query("SELECT * FROM locker WHERE transActionType = 'ADD' ")
    fun getADDLockerTransactions(): Flow<List<Locker>>


}