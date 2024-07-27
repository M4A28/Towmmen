package com.mohmmed.mosa.eg.towmmen.domin.usecases.product

import com.mohmmed.mosa.eg.towmmen.data.repository.ProductRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.module.Product
import javax.inject.Inject

class UpsertProduct @Inject constructor(
    private val productRepository: ProductRepositoryImp
) {
    suspend operator fun invoke(product: Product){
        productRepository.upsertProduct(product)
    }

}
