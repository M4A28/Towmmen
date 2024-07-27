package com.mohmmed.mosa.eg.towmmen.domin.usecases.product

import com.mohmmed.mosa.eg.towmmen.data.repository.ProductRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TotalProductsCost @Inject constructor(
    private val productRepositoryImp: ProductRepositoryImp
) {
    operator fun invoke(): Flow<Double?> {
        return productRepositoryImp.getTotalCostOfProducts()
    }
}