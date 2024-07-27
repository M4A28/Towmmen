package com.mohmmed.mosa.eg.towmmen.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.mohmmed.mosa.eg.towmmen.domin.module.Product
import com.mohmmed.mosa.eg.towmmen.domin.module.ProductWithCustomers
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ProductDao {

    @Query("SELECT * FROM products ORDER BY createdAt DESC")
    fun getAllProducts(): Flow<List<Product>>

    @Upsert
    suspend fun upsertProduct(product: Product)

    @Query("SELECT SUM((price * stockQuantity)) FROM products")
    fun getTotalCostOfProducts(): Flow<Double?>

    @Query("SELECT * FROM products WHERE expireDate = :expDate ORDER BY expireDate DESC")
    fun getProductsByItExpDate(expDate: Date): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE productId = :id ORDER BY createdAt DESC")
    fun getProductById(id: Int): Flow<Product>

    @Query("SELECT * FROM products WHERE name = :name ORDER BY createdAt DESC")
    fun getProductByName(name: String): Flow<List<Product>>
    @Query("SELECT * FROM products WHERE name = :barcode ORDER BY createdAt DESC")
    fun getProductByBarcode(barcode: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE category = :category ORDER BY createdAt DESC")
    fun getProductsByCategory(category: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE stockQuantity < :threshold ORDER BY createdAt DESC")
    fun getLowStockProducts(threshold: Int): Flow<List<Product>>

    @Query("SELECT count(*) FROM products")
    fun getProductCount(): Flow<Int?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Transaction
    @Query("SELECT * FROM products ORDER BY createdAt DESC")
    fun getProductsWithCustomers(): Flow<List<ProductWithCustomers>>




}