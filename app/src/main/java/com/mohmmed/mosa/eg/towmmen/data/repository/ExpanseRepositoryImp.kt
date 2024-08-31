package com.mohmmed.mosa.eg.towmmen.data.repository

import com.mohmmed.mosa.eg.towmmen.data.local.dao.ExpanseDao
import com.mohmmed.mosa.eg.towmmen.data.module.Expanse
import com.mohmmed.mosa.eg.towmmen.data.module.ExpansePerPeriod
import com.mohmmed.mosa.eg.towmmen.domin.repostory.ExpanseRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class ExpanseRepositoryImp @Inject constructor(
    private val expanseDao: ExpanseDao
) : ExpanseRepository {
    override suspend fun upsertExpanse(expanse: Expanse) {
        expanseDao.upsertExpanse(expanse)
    }

    override suspend fun deleteExpanse(expanse: Expanse) {
        expanseDao.deleteExpanse(expanse)
    }

    override fun getAllExpanse(): Flow<List<Expanse>> {
        return expanseDao.getAllExpanse()
    }

    override fun getExpanseInRange(start: Date, end: Date): Flow<List<Expanse>> {
        return expanseDao.getExpanseInRange(start, end)
    }

    override fun getExpansePerMonth(): Flow<List<ExpansePerPeriod>> {
        return expanseDao.getExpansePerMonth()
    }

    override fun getAvgExpanse(): Flow<Double?> {
        return expanseDao.getAvgExpanse()
    }

    override fun getAvgExpansePerMonth(): Flow<Double?> {
        return expanseDao.getAvgExpansePerMonth()
    }

    override suspend fun clearExpanseDate() {
        expanseDao.clearExpanseDate()
    }

    override fun getExpansePerDay(): Flow<List<ExpansePerPeriod>> {
        return expanseDao.getExpansePerDay()
    }

    override fun getExpansePerWeek(): Flow<List<ExpansePerPeriod>> {
        return expanseDao.getExpansePerWeek()
    }
}