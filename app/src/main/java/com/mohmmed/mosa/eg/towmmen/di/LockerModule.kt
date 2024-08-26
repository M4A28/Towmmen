package com.mohmmed.mosa.eg.towmmen.di

import com.mohmmed.mosa.eg.towmmen.data.local.dao.LockerDao
import com.mohmmed.mosa.eg.towmmen.data.local.db.TowmmenDatabase
import com.mohmmed.mosa.eg.towmmen.data.repository.LockerRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker.ClearLocker
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker.DeleteLocker
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker.GetAddLocker
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker.GetAllLocker
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker.GetSubLocker
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker.LockerUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.locker.UpsertLocker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LockerModule {
    @Provides
    @Singleton
    fun provideLockerDao(towmmenDatabase: TowmmenDatabase): LockerDao {
        return towmmenDatabase.lockerDao
    }

    @Provides
    @Singleton
    fun provideLockerRepository(lockerDao: LockerDao): LockerRepositoryImp {
        return LockerRepositoryImp(lockerDao)
    }

    @Provides
    @Singleton
    fun provideLockerUseCases(lockerRepository: LockerRepositoryImp): LockerUseCases {
        return LockerUseCases(
            upsertLocker = UpsertLocker(lockerRepository),
            deleteLocker = DeleteLocker(lockerRepository),
            getAllLocker = GetAllLocker(lockerRepository),
            getSubLocker = GetSubLocker(lockerRepository),
            getAddLocker = GetAddLocker(lockerRepository),
           clearLocker = ClearLocker(lockerRepository),
        )
    }

}