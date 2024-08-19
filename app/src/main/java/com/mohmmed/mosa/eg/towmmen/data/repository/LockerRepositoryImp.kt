package com.mohmmed.mosa.eg.towmmen.data.repository

import com.mohmmed.mosa.eg.towmmen.data.local.dao.LockerDao
import com.mohmmed.mosa.eg.towmmen.data.module.Locker
import com.mohmmed.mosa.eg.towmmen.domin.repostory.LockerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LockerRepositoryImp @Inject constructor(
    private val lockerDao: LockerDao
) : LockerRepository {
    override suspend fun upsertLockerTransaction(locker: Locker) {
        lockerDao.upsertLockerTransaction(locker)
    }

    override suspend fun deleteLockerTransaction(locker: Locker) {
        lockerDao.deleteLockerTransaction(locker)
    }

    override fun getAllLockerTransactions(): Flow<List<Locker>> {
        return lockerDao.getAllLockerTransactions()
    }

    override fun getSubLockerTransactions(): Flow<List<Locker>> {
        return lockerDao.getSubLockerTransactions()
    }

    override fun getADDLockerTransactions(): Flow<List<Locker>> {
        return lockerDao.getADDLockerTransactions()
    }

}