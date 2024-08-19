package com.mohmmed.mosa.eg.towmmen.domin.repostory

import com.mohmmed.mosa.eg.towmmen.data.module.CategoryCount
import com.mohmmed.mosa.eg.towmmen.data.module.MonthlyAvgPrice
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ProductRepository {

    suspend fun addNewProduct(product: Product)

    fun getProductByBarcode(barcode: String): Flow<Product>

    fun getProductsExpiringBetween(startDate: Date, endDate: Date): Flow<List<Product>>


    suspend fun upsertProduct(product: Product)

    fun getAllProducts(): Flow<List<Product>>

    fun getProductByName(name: String): Flow<List<Product>>

    suspend fun deleteProduct(product: Product)

    suspend fun updateProduct(product: Product)

    fun getTotalCostOfProducts(): Flow<Double?>

    fun getProductCount(): Flow<Int?>
    fun getAveragePrice(): Flow<Double?>

    fun getTotalStock(): Flow<Int?>

    fun getProductCountByCategory(): Flow<List<CategoryCount>>

    fun getMaxPrice(): Flow<Double?>

    fun getMinPrice(): Flow<Double?>
    fun getProductById(id: Int): Flow<Product>


    fun getAveragePriceByMonth(): Flow<List<MonthlyAvgPrice>>

}