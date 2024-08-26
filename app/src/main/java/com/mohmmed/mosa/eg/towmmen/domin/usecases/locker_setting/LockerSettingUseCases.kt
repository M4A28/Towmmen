package com.mohmmed.mosa.eg.towmmen.domin.usecases.locker_setting

data class LockerSettingUseCases(
    val setCanAddSellToDb: SetCanAddSellToDb,
    val getCanAddSellToDb: GetCanAddSellToDb,
    val setCanAddBuyToDb: SetCanAddBuyToDb,
    val getCanAddBuyToDb: GetCanAddBuyToDb,
    val setCanSaveExpanseToDb: SetCanSaveExpanseToDb,
    val getCanSaveExpanseToDb: GetCanSaveExpanseToDb
)