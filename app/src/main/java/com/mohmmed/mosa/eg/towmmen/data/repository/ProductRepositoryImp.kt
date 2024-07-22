package com.mohmmed.mosa.eg.towmmen.data.repository

import com.mohmmed.mosa.eg.towmmen.data.local.dao.ProductDao
import com.mohmmed.mosa.eg.towmmen.domin.module.Product
import com.mohmmed.mosa.eg.towmmen.domin.repostory.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImp @Inject constructor(
    private val productDao: ProductDao
) : ProductRepository {
    override suspend fun addNewProduct(product: Product) {
        productDao.insertProduct(product)
    }

    override fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }

    override fun getProductByName(name: String): Flow<List<Product>> {
        return productDao.getProductByName(name)
    }

    override suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

    override suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    override fun getProductCount(): Flow<Int> {
        return productDao.getProductCount()
    }
}