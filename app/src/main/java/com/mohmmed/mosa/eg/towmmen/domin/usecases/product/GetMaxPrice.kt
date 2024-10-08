package com.mohmmed.mosa.eg.towmmen.domin.usecases.product

import com.mohmmed.mosa.eg.towmmen.data.repository.ProductRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMaxPrice @Inject constructor(
    private val productRepository: ProductRepositoryImp
) {

    operator fun invoke(): Flow<Double?> {
        return productRepository.getMaxPrice()
    }

}
