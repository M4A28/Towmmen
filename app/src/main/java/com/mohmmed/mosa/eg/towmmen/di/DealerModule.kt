package com.mohmmed.mosa.eg.towmmen.di

import com.mohmmed.mosa.eg.towmmen.data.local.dao.DealerDao
import com.mohmmed.mosa.eg.towmmen.data.local.db.TowmmenDatabase
import com.mohmmed.mosa.eg.towmmen.data.repository.DealerRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.usecases.dealer.DealerUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.dealer.DeleteDealer
import com.mohmmed.mosa.eg.towmmen.domin.usecases.dealer.GetAllDealers
import com.mohmmed.mosa.eg.towmmen.domin.usecases.dealer.GetDealerById
import com.mohmmed.mosa.eg.towmmen.domin.usecases.dealer.UpsertDealer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DealerModule {

    @Provides
    @Singleton
    fun provideDealerDao(towmenDb: TowmmenDatabase): DealerDao {
        return towmenDb.dealerDao
    }

    @Provides
    @Singleton
    fun provideDealerRepository(dealerDao: DealerDao): DealerRepositoryImp {
        return DealerRepositoryImp(dealerDao)
    }

    @Provides
    @Singleton
    fun provideDealerUseCases(dealerRepositoryImp: DealerRepositoryImp): DealerUseCases {
        return DealerUseCases(
            upsertDealer = UpsertDealer(dealerRepositoryImp),
            deleteDealer = DeleteDealer(dealerRepositoryImp),
            getAllDealers = GetAllDealers(dealerRepositoryImp),
            getDealerById = GetDealerById(dealerRepositoryImp),
        )
    }





}