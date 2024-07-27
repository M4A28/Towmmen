package com.mohmmed.mosa.eg.towmmen.domin.repostory

import com.mohmmed.mosa.eg.towmmen.domin.module.CustomerWithPurchases
import com.mohmmed.mosa.eg.towmmen.domin.module.Purchase
import com.mohmmed.mosa.eg.towmmen.domin.module.PurchaseWithDetails
import kotlinx.coroutines.flow.Flow

interface PurchaseRepository {

    fun getAllPurchases(): Flow<List<Purchase>>

    fun getCustomersWithPurchases(): Flow<List<CustomerWithPurchases>>

    fun getPurchasesWithDetails(): Flow<List<PurchaseWithDetails>>

    suspend fun insertPurchase(purchase: Purchase)

    suspend fun deletePurchase(purchase: Purchase)

    fun getPurchasesByCustomerId(customerId: Int): Flow<List<Purchase>>

    fun getTotalPurchases(): Flow<Int>

    fun getTotalPurchasesByCustomer(customerId: Int): Flow<Int>

}