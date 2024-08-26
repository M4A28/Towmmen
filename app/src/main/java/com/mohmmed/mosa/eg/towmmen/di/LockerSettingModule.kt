package com.mohmmed.mosa.eg.towmmen.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mohmmed.mosa.eg.towmmen.data.localusermanger.LockerSettingRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker_setting.GetCanAddBuyToDb
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker_setting.GetCanAddSellToDb
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker_setting.GetCanSaveExpanseToDb
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker_setting.LockerSettingUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker_setting.SetCanAddBuyToDb
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker_setting.SetCanAddSellToDb
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker_setting.SetCanSaveExpanseToDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LockerSettingModule {

    @Provides
    @Singleton
    fun provideLockerSettingRepository(dataStore: DataStore<Preferences>): LockerSettingRepositoryImp {
        return LockerSettingRepositoryImp(dataStore = dataStore)
    }

    @Provides
    @Singleton
    fun provideLockerSettingCases(lockerSettingRepositoryImp: LockerSettingRepositoryImp): LockerSettingUseCases {
        return LockerSettingUseCases(
            setCanAddSellToDb = SetCanAddSellToDb(lockerSettingRepositoryImp),
            getCanAddSellToDb = GetCanAddSellToDb(lockerSettingRepositoryImp),
            setCanAddBuyToDb = SetCanAddBuyToDb(lockerSettingRepositoryImp),
            getCanAddBuyToDb = GetCanAddBuyToDb(lockerSettingRepositoryImp),
            setCanSaveExpanseToDb = SetCanSaveExpanseToDb(lockerSettingRepositoryImp),
            getCanSaveExpanseToDb = GetCanSaveExpanseToDb(lockerSettingRepositoryImp)
        )
    }
}