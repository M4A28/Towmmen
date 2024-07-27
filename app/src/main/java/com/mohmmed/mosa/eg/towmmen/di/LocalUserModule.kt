package com.mohmmed.mosa.eg.towmmen.di

import android.app.Application
import com.mohmmed.mosa.eg.towmmen.data.localusermanger.LocalUserMangerImpl
import com.mohmmed.mosa.eg.towmmen.domin.localusermanger.LocalUserManger
import com.mohmmed.mosa.eg.towmmen.domin.usecases.localuser.AppEntryUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.localuser.ReadAppEntry
import com.mohmmed.mosa.eg.towmmen.domin.usecases.localuser.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalUserModule {

    @Provides
    @Singleton
    fun provideLocalUserManger(
        application: Application
    ): LocalUserManger = LocalUserMangerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManger: LocalUserManger): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManger),
        saveAppEntry = SaveAppEntry(localUserManger)
    )
}