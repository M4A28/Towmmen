package com.mohmmed.mosa.eg.towmmen.di

import com.mohmmed.mosa.eg.towmmen.data.local.dao.ExpanseDao
import com.mohmmed.mosa.eg.towmmen.data.local.db.TowmmenDatabase
import com.mohmmed.mosa.eg.towmmen.data.repository.ExpanseRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse.ClearExpanseData
import com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse.DeleteExpanse
import com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse.ExpanseUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse.GetAllExpanse
import com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse.GetAvgExpanse
import com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse.GetAvgExpansePerMonth
import com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse.GetExpanseInRange
import com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse.GetExpansePerDay
import com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse.GetExpansePerMonth
import com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse.GetExpansePerWeek
import com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse.UpsertExpanse
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
    @Provides
    @Singleton
    fun provideExpanseUseCase(expanseRepo: ExpanseRepositoryImp): ExpanseUseCases{
        return ExpanseUseCases(
            upsertExpanse = UpsertExpanse(expanseRepo),
            deleteExpanse = DeleteExpanse(expanseRepo),
            getAllExpanse = GetAllExpanse(expanseRepo),
            getExpanseInRange = GetExpanseInRange(expanseRepo),
            getExpansePerMonth =  GetExpansePerMonth(expanseRepo),
            getAvgExpanse = GetAvgExpanse(expanseRepo),
            getAvgExpansePerMonth = GetAvgExpansePerMonth(expanseRepo),
            clearExpanseData = ClearExpanseData(expanseRepo),
            getExpansePerWeek = GetExpansePerWeek(expanseRepo),
            getExpansePerDay = GetExpansePerDay(expanseRepo),
        )
    }
}