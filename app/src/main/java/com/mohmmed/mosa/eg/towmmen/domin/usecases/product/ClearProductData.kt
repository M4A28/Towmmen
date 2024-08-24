package com.mohmmed.mosa.eg.towmmen.domin.usecases.product

import com.mohmmed.mosa.eg.towmmen.data.repository.ProductRepositoryImp
import javax.inject.Inject

class ClearProductData @Inject constructor(
    private val productRepository: ProductRepositoryImp
) {

    suspend operator fun invoke(){
        productRepository.clearProductsDate()
    }

}
