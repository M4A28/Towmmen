package com.mohmmed.mosa.eg.towmmen.data.repository

import com.mohmmed.mosa.eg.towmmen.data.local.dao.ProductDao
import com.mohmmed.mosa.eg.towmmen.data.module.CategoryCount
import com.mohmmed.mosa.eg.towmmen.data.module.MonthlyAvgPrice
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.domin.repostory.ProductRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class ProductRepositoryImp @Inject constructor(
    private val productDao: ProductDao
) : ProductRepository {
    override suspend fun addNewProduct(product: Product) {
        productDao.insertProduct(product)
    }

    override fun getProductByBarcode(barcode: String): Flow<Product> {
        return productDao.getProductByBarcode(barcode)
    }

    override fun getProductsExpiringBetween(startDate: Date, endDate: Date): Flow<List<Product>> {
        return productDao.getProductsExpiringBetween(startDate, endDate)
    }


    override suspend fun upsertProduct(product: Product) {
        productDao.upsertProduct(product)
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

    override fun getProductCount(): Flow<Int?> {
        return productDao.getProductCount()
    }

    override fun getAveragePrice(): Flow<Double?> {
        return productDao.getAveragePrice()
    }

    override fun getTotalStock(): Flow<Int?> {
        return productDao.getTotalStock()
    }

    override fun getProductCountByCategory(): Flow<List<CategoryCount>> {
        return productDao.getProductCountByCategory()
    }

    override fun getMaxPrice(): Flow<Double?> {
        return productDao.getMaxPrice()
    }

    override fun getMinPrice(): Flow<Double?> {
        return productDao.getMinPrice()
    }

    override fun getAveragePriceByMonth(): Flow<List<MonthlyAvgPrice>> {
        return productDao.getAveragePriceByMonth()
    }

    override fun getTotalCostOfProducts(): Flow<Double?> {
        return productDao.getTotalCostOfProducts()
    }
}