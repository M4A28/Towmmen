package com.mohmmed.mosa.eg.towmmen.domin.usecases.locker_setting

import com.mohmmed.mosa.eg.towmmen.data.localusermanger.LockerSettingRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCanSaveExpanseToDb @Inject constructor(
    private val lockerSettingRepositoryImp: LockerSettingRepositoryImp
){
    operator fun invoke(): Flow<Boolean> {
        return lockerSettingRepositoryImp.getCanSaveExpanseToDb()
    }

}