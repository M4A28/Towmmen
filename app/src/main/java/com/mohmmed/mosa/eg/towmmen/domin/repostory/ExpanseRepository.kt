package com.mohmmed.mosa.eg.towmmen.domin.repostory

import com.mohmmed.mosa.eg.towmmen.data.module.Expanse
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ExpanseRepository {
    suspend fun upsertExpanse(expanse: Expanse)

    suspend fun deleteExpanse(expanse: Expanse)


    fun getAllExpanse(): Flow<List<Expanse>>

    fun getExpanseInRange(start: Date, end: Date): Flow<List<Expanse>>
}