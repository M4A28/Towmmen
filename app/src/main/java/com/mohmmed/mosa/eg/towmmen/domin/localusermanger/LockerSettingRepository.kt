package com.mohmmed.mosa.eg.towmmen.domin.localusermanger

import kotlinx.coroutines.flow.Flow

interface LockerSettingRepository {

    suspend fun setCanAddSellToDb(canSave: Boolean)

    fun getCanAddSellToDb(): Flow<Boolean>

    suspend fun setCanAddBuyToDb(canSave: Boolean)

    fun getCanAddBuyToDb(): Flow<Boolean>

    suspend fun setCanSaveExpanseToDb(canSave: Boolean)

    fun getCanSaveExpanseToDb(): Flow<Boolean>


}