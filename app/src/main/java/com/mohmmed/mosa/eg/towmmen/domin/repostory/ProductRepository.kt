package com.mohmmed.mosa.eg.towmmen.domin.repostory

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

    fun getProductCount(): Flow<Int?>

    fun getTotalCostOfProducts(): Flow<Double?>



}