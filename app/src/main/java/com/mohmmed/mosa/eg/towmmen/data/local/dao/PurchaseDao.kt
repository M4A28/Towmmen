package com.mohmmed.mosa.eg.towmmen.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.mohmmed.mosa.eg.towmmen.data.module.Purchase
import com.mohmmed.mosa.eg.towmmen.data.module.PurchaseItem
import com.mohmmed.mosa.eg.towmmen.data.module.PurchaseWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchaseDao {
    @Query("SELECT * FROM purchases")
    fun getAllPurchases(): Flow<List<Purchase>>

    @Insert
    suspend fun insertPurchase(purchase: Purchase)
    @Delete
    suspend fun deletePurchase(purchase: Purchase)

    @Query("SELECT * FROM purchases WHERE dealerId = :dealerId")
    fun getPurchasesByCustomerId(dealerId: Int): Flow<List<Purchase>>

    @Query("SELECT COUNT(*) FROM purchases")
    fun getTotalPurchases(): Flow<Int>

    @Query("SELECT COUNT(*) FROM purchases WHERE dealerId = :dealerId")
    fun getTotalPurchasesByCustomer(dealerId: Int): Flow<Int>


    @Upsert
    suspend fun upsertPurchaseWithItems(items: List<PurchaseItem>)

    @Transaction
    @Query("SELECT * FROM purchases ORDER BY date DESC ")
    fun getPurchaseWithItems(): Flow<List<PurchaseWithItems>>

    @Transaction
    @Query("SELECT * FROM purchases WHERE purchaseId = :purchaseId")
    fun getPurchaseWithItems(purchaseId: String): Flow<PurchaseWithItems?>

    @Transaction
    @Query("SELECT * FROM purchases WHERE dealerId = :dealerId ORDER BY date DESC ")
    fun getPurchaseWithItemsByDealerId(dealerId: Int): Flow<List<PurchaseWithItems>>

    @Transaction
    suspend fun insertFullPurchase(purchase: Purchase, items: List<PurchaseItem>) {
        insertPurchase(purchase)
        val purchaseId  = purchase.purchaseId
        items.forEach { item ->
            upsertPurchaseWithItems(listOf(item.copy(purchaseId = purchaseId)))
        }
    }

    @Query("DELETE FROM purchases")
    suspend fun clearPurchasesDate()

}