package com.mohmmed.mosa.eg.towmmen.domin.usecases.locker_setting

import com.mohmmed.mosa.eg.towmmen.data.localusermanger.LockerSettingRepositoryImp
import javax.inject.Inject

class SetCanAddSellToDb @Inject constructor(
    private val lockerSettingRepositoryImp: LockerSettingRepositoryImp
){
    suspend operator fun invoke(canSave: Boolean) {
        lockerSettingRepositoryImp.setCanAddSellToDb(canSave)
    }
}