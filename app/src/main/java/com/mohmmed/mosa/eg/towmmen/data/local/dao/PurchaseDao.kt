package com.mohmmed.mosa.eg.towmmen.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mohmmed.mosa.eg.towmmen.data.module.Purchase
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



}