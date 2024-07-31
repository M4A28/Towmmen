package com.mohmmed.mosa.eg.towmmen.di

import com.mohmmed.mosa.eg.towmmen.data.local.dao.ProductDao
import com.mohmmed.mosa.eg.towmmen.data.local.db.TowmmenDatabase
import com.mohmmed.mosa.eg.towmmen.data.repository.ProductRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.AddNewProduct
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.DeleteProduct
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.GetAllProducts
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.GetProduct
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.GetProductByBarcode
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.GetProductsExpiringBetween
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.ProductBarcode
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.ProductUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.ProductsCount
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.TotalProductsCost
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.UpdateProduct
import com.mohmmed.mosa.eg.towmmen.domin.usecases.product.UpsertProduct
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductModule {

    @Provides
    @Singleton
    fun provideProductDao(towmmenDatabase: TowmmenDatabase): ProductDao {
        return towmmenDatabase.productDao
    }

    @Provides
    @Singleton
    fun provideProductRepository(productDao: ProductDao): ProductRepositoryImp {
        return ProductRepositoryImp(productDao)
    }

    @Provides
    @Singleton
    fun provideProductUseCases(productRepositoryImp: ProductRepositoryImp): ProductUseCases {
        return ProductUseCases(
            deleteProduct = DeleteProduct(productRepositoryImp),
            addNewProduct = AddNewProduct(productRepositoryImp),
            updateProduct = UpdateProduct(productRepositoryImp),
            upsertProduct = UpsertProduct(productRepositoryImp),
            getALlProducts = GetAllProducts(productRepositoryImp),
            getProduct = GetProduct(productRepositoryImp),
            getProductsCount = ProductsCount(productRepositoryImp),
            productBarcode = ProductBarcode(productRepositoryImp),
            totalProductsCost = TotalProductsCost(productRepositoryImp),
            getProductsExpiringBetween = GetProductsExpiringBetween(productRepositoryImp),
            getProductByBarcode = GetProductByBarcode(productRepositoryImp)
        )
    }
}