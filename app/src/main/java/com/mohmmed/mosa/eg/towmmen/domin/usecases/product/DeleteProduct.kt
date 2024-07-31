package com.mohmmed.mosa.eg.towmmen.domin.usecases.product

import com.mohmmed.mosa.eg.towmmen.data.repository.ProductRepositoryImp
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import javax.inject.Inject

class DeleteProduct @Inject constructor(
    private val productRepository: ProductRepositoryImp
) {

     suspend operator fun invoke(product: Product){
         productRepository.deleteProduct(product)
    }

}
