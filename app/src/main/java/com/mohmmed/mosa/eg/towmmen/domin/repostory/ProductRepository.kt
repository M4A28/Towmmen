package com.mohmmed.mosa.eg.towmmen.domin.repostory

import com.mohmmed.mosa.eg.towmmen.domin.module.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun addNewProduct(product: Product)

    fun getAllProducts(): Flow<List<Product>>

    fun getProductByName(name: String): Flow<List<Product>>

    suspend fun deleteProduct(product: Product)

    suspend fun updateProduct(product: Product)

    fun getProductCount(): Flow<Int>


}