package com.mohmmed.mosa.eg.towmmen.domin.usecases.locker

import com.mohmmed.mosa.eg.towmmen.data.module.Locker
import com.mohmmed.mosa.eg.towmmen.data.repository.LockerRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSubLocker @Inject constructor(
    private val lockerRepositoryImp: LockerRepositoryImp
){
    operator fun  invoke(): Flow<List<Locker>> {
        return lockerRepositoryImp.getSubLockerTransactions()
    }
}