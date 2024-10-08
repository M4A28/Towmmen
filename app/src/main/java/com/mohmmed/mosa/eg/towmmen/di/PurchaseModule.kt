package com.mohmmed.mosa.eg.towmmen.di

import com.mohmmed.mosa.eg.towmmen.data.local.dao.PurchaseDao
import com.mohmmed.mosa.eg.towmmen.data.local.db.TowmmenDatabase
import com.mohmmed.mosa.eg.towmmen.data.repository.PurchaseRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase.ClearPurchasesDate
import com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase.DeletePurchase
import com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase.GetAllPurchases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase.GetPurchaseWithItems
import com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase.GetPurchaseWithItemsByDealerId
import com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase.GetPurchasesByCustomerId
import com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase.GetTotalPurchases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase.GetTotalPurchasesByCustomer
import com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase.InsertFullPurchase
import com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase.InsertPurchase
import com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase.PurchaseUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase.UpsertPurchaseWithItems
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PurchaseModule {

    @Provides
    @Singleton
    fun providePurchaseDao(
        tommenDatabase: TowmmenDatabase
    ): PurchaseDao {
        return tommenDatabase.purchaseDao
    }


    @Provides
    @Singleton
    fun providePurchaseRepository(
        purchaseDao: PurchaseDao
    ): PurchaseRepositoryImp {
        return PurchaseRepositoryImp(purchaseDao)
    }

    @Provides
    @Singleton
    fun providePurchaseUseCases(
        purchaseRepository: PurchaseRepositoryImp
    ): PurchaseUseCases {
        return PurchaseUseCases(
            getAllPurchases = GetAllPurchases(purchaseRepository),
            getTotalPurchasesByCustomer = GetTotalPurchasesByCustomer(purchaseRepository),
            getPurchasesByCustomerId = GetPurchasesByCustomerId(purchaseRepository),
            getTotalPurchases = GetTotalPurchases(purchaseRepository),
            insertPurchase = InsertPurchase(purchaseRepository),
            deletePurchase = DeletePurchase(purchaseRepository),
            clearPurchasesDate = ClearPurchasesDate(purchaseRepository),
            getPurchaseWithItems = GetPurchaseWithItems(purchaseRepository),
            getPurchaseWithItemsByDealerId = GetPurchaseWithItemsByDealerId(purchaseRepository),
            insertFullPurchase = InsertFullPurchase(purchaseRepository),
            upsertPurchaseWithItems = UpsertPurchaseWithItems(purchaseRepository),
        )
    }
}