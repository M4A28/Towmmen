package com.mohmmed.mosa.eg.towmmen.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.mohmmed.mosa.eg.towmmen.data.module.CategoryCount
import com.mohmmed.mosa.eg.towmmen.data.module.MonthlyAvgPrice
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ProductDao {

    @Query("SELECT * FROM products ORDER BY updatedAt DESC")
    fun getAllProducts(): Flow<List<Product>>

    @Upsert
    suspend fun upsertProduct(product: Product)

    @Query("SELECT SUM((cost * stockQuantity)) FROM products")
    fun getTotalCostOfProducts(): Flow<Double?>

    @Query("SELECT * FROM products WHERE expireDate BETWEEN :startDate AND :endDate")
    fun getProductsExpiringBetween(startDate: Date, endDate: Date): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE productId = :id ORDER BY createdAt DESC")
    fun getProductById(id: Int): Flow<Product>

    @Query("SELECT * FROM products WHERE name = :name ORDER BY createdAt DESC")
    fun getProductByName(name: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE barcode = :barcode")
    fun getProductByBarcode(barcode: String): Flow<Product>

    @Query("SELECT * FROM products WHERE category = :category ORDER BY createdAt DESC")
    fun getProductsByCategory(category: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE stockQuantity < :threshold ORDER BY createdAt DESC")
    fun getLowStockProducts(threshold: Int): Flow<List<Product>>

    @Query("SELECT count(*) FROM products")
    fun getProductCount(): Flow<Int?>

    @Query("SELECT AVG(price) FROM products")
    fun getAveragePrice(): Flow<Double?>

    @Query("SELECT SUM(stockQuantity) FROM products")
    fun getTotalStock(): Flow<Int?>

    @Query("SELECT category, COUNT(*) as count FROM products GROUP BY category")
    fun getProductCountByCategory(): Flow<List<CategoryCount>>

    @Query("SELECT MAX(price) FROM products")
    fun getMaxPrice(): Flow<Double?>

    @Query("SELECT MIN(price) FROM products")
    fun getMinPrice(): Flow<Double?>

    @Query("SELECT strftime('%Y-%m', createdAt) as month, AVG(price) as avgPrice " +
            "FROM products GROUP BY month ORDER BY month")
    fun getAveragePriceByMonth(): Flow<List<MonthlyAvgPrice>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM products")
    suspend fun clearProductsDate()



}