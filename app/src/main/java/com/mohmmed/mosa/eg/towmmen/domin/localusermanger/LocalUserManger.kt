package com.mohmmed.mosa.eg.towmmen.domin.localusermanger

import kotlinx.coroutines.flow.Flow

interface LocalUserManger {
    suspend fun saveAppEntry()
    fun readAppEntry(): Flow<Boolean>

    suspend fun saveLastInvoiceNumber(lastNumber: Int)
    fun readLastInvoiceNumber(): Flow<Int>
}