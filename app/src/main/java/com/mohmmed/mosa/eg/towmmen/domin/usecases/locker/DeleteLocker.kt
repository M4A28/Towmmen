package com.mohmmed.mosa.eg.towmmen.domin.usecases.locker

import com.mohmmed.mosa.eg.towmmen.data.module.Locker
import com.mohmmed.mosa.eg.towmmen.data.repository.LockerRepositoryImp
import javax.inject.Inject

class DeleteLocker @Inject constructor(
private val lockerRepositoryImp: LockerRepositoryImp
){
    suspend operator fun  invoke(locker: Locker){
        lockerRepositoryImp.deleteLockerTransaction(locker)
    }
}