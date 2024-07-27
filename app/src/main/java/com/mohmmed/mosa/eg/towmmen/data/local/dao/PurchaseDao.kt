package com.mohmmed.mosa.eg.towmmen.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.mohmmed.mosa.eg.towmmen.domin.module.CustomerWithPurchases
import com.mohmmed.mosa.eg.towmmen.domin.module.Purchase
import com.mohmmed.mosa.eg.towmmen.domin.module.PurchaseWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchaseDao {
    @Query("SELECT * FROM purchases")
    fun getAllPurchases(): Flow<List<Purchase>>

    @Transaction
    @Query("SELECT * FROM customers")
    fun getCustomersWithPurchases(): Flow<List<CustomerWithPurchases>>

    @Transaction
    @Query("SELECT * FROM purchases")
    fun getPurchasesWithDetails(): Flow<List<PurchaseWithDetails>>

    @Insert
    suspend fun insertPurchase(purchase: Purchase)
    @Delete
    suspend fun deletePurchase(purchase: Purchase)

    @Query("SELECT * FROM purchases WHERE customerId = :customerId")
    fun getPurchasesByCustomerId(customerId: Int): Flow<List<Purchase>>

    @Query("SELECT COUNT(*) FROM purchases")
    fun getTotalPurchases(): Flow<Int>

    @Query("SELECT COUNT(*) FROM purchases WHERE customerId = :customerId")
    fun getTotalPurchasesByCustomer(customerId: Int): Flow<Int>



}