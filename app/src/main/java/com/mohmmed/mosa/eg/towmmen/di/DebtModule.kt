package com.mohmmed.mosa.eg.towmmen.di

import com.mohmmed.mosa.eg.towmmen.data.local.dao.DebtDao
import com.mohmmed.mosa.eg.towmmen.data.local.db.TowmmenDatabase
import com.mohmmed.mosa.eg.towmmen.data.repository.DebtRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.usecases.debt.DebtUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.debt.DeleteDebt
import com.mohmmed.mosa.eg.towmmen.domin.usecases.debt.DeleteDebtItems
import com.mohmmed.mosa.eg.towmmen.domin.usecases.debt.GetAllDebtWithItems
import com.mohmmed.mosa.eg.towmmen.domin.usecases.debt.GetDebtByCustomerId
import com.mohmmed.mosa.eg.towmmen.domin.usecases.debt.GetDebtByCustomerName
import com.mohmmed.mosa.eg.towmmen.domin.usecases.debt.GetNotPaidDebtWithItems
import com.mohmmed.mosa.eg.towmmen.domin.usecases.debt.GetPaidDebtWithItems
import com.mohmmed.mosa.eg.towmmen.domin.usecases.debt.GetTotalDebt
import com.mohmmed.mosa.eg.towmmen.domin.usecases.debt.InsertFullDebt
import com.mohmmed.mosa.eg.towmmen.domin.usecases.debt.UpsertDebt
import com.mohmmed.mosa.eg.towmmen.domin.usecases.debt.UpsertDebtItems
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DebtModule {
    @Provides
    @Singleton
    fun provideDebtDao(towmenDb: TowmmenDatabase): DebtDao {
        return towmenDb.debtDao
    }

    @Provides
    @Singleton
    fun provideDebtRepository(debtDao: DebtDao): DebtRepositoryImp {
        return DebtRepositoryImp(debtDao)
    }

    @Provides
    @Singleton
    fun provideDebtUseCases(debtRepositoryImp: DebtRepositoryImp): DebtUseCases {
        return DebtUseCases(
            upsertDebt = UpsertDebt(debtRepositoryImp),
            upsertDebtItems = UpsertDebtItems(debtRepositoryImp),
            insertFullDebt = InsertFullDebt(debtRepositoryImp),
            deleteDebt = DeleteDebt(debtRepositoryImp),
            deleteDebtItems = DeleteDebtItems(debtRepositoryImp),
            getAllDebtWithItems = GetAllDebtWithItems(debtRepositoryImp),
            getPaidDebtWithItems = GetPaidDebtWithItems(debtRepositoryImp),
            getNotPaidDebtWithItems = GetNotPaidDebtWithItems(debtRepositoryImp),
            getTotalDebt = GetTotalDebt(debtRepositoryImp),
            getDebtByCustomerId = GetDebtByCustomerId(debtRepositoryImp),
            getDebtByCustomerName = GetDebtByCustomerName(debtRepositoryImp)
        )
    }



}