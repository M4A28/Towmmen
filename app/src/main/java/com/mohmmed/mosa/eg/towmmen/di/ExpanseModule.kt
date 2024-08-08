package com.mohmmed.mosa.eg.towmmen.di

import com.mohmmed.mosa.eg.towmmen.data.local.dao.ExpanseDao
import com.mohmmed.mosa.eg.towmmen.data.local.db.TowmmenDatabase
import com.mohmmed.mosa.eg.towmmen.data.repository.ExpanseRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ExpanseModule {


    @Provides
    @Singleton
    fun provideExpanseDao(towmenDb: TowmmenDatabase): ExpanseDao{
        return towmenDb.expanseDao
    }


    @Provides
    @Singleton
    fun provideExpanseRepository(expanseDao: ExpanseDao): ExpanseRepositoryImp{
        return ExpanseRepositoryImp(expanseDao)
    }




}