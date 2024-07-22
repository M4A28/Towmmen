package com.mohmmed.mosa.eg.towmmen.di

import com.mohmmed.mosa.eg.towmmen.data.local.dao.EmployeeDao
import com.mohmmed.mosa.eg.towmmen.data.local.db.TowmmenDatabase
import com.mohmmed.mosa.eg.towmmen.data.repository.EmployeeRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EmployeeModule {

    @Provides
    @Singleton
    fun provideEmployeeDao(towmmenDatabase: TowmmenDatabase): EmployeeDao {
        return towmmenDatabase.employeeDao

    }

    @Provides
    @Singleton
    fun provideEmployeeRepository(employeeDao: EmployeeDao): EmployeeRepositoryImp {
        return EmployeeRepositoryImp(employeeDao)

    }
}