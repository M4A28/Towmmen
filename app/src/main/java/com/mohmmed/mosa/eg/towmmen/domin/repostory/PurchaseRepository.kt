package com.mohmmed.mosa.eg.towmmen.domin.repostory

import com.mohmmed.mosa.eg.towmmen.data.module.Purchase
import com.mohmmed.mosa.eg.towmmen.data.module.PurchaseItem
import com.mohmmed.mosa.eg.towmmen.data.module.PurchaseWithItems
import kotlinx.coroutines.flow.Flow

interface PurchaseRepository {

    fun getAllPurchases(): Flow<List<Purchase>>

    suspend fun insertPurchase(purchase: Purchase)

    suspend fun deletePurchase(purchase: Purchase)

    fun getPurchasesByCustomerId(customerId: Int): Flow<List<Purchase>>

    fun getTotalPurchases(): Flow<Int>

    fun getTotalPurchasesByCustomer(customerId: Int): Flow<Int>

    suspend fun upsertPurchaseWithItems(items: List<PurchaseItem>)

    fun getPurchaseWithItems(): Flow<List<PurchaseWithItems>>

    fun getPurchaseWithItems(purchaseId: String): Flow<PurchaseWithItems?>

    fun getPurchaseWithItemsByDealerId(dealerId: Int): Flow<List<PurchaseWithItems>>

    suspend fun insertFullPurchase(purchase: Purchase, items: List<PurchaseItem>)

    suspend fun clearPurchasesDate()

}