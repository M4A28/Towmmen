package com.mohmmed.mosa.eg.towmmen.domin.usecases.product

import com.mohmmed.mosa.eg.towmmen.data.repository.ProductRepositoryImp
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductBarcode @Inject constructor(
    private val productRepository: ProductRepositoryImp
) {
    operator fun invoke(barcode: String): Flow<Product> {
        return productRepository.getProductByBarcode(barcode)
    }
}