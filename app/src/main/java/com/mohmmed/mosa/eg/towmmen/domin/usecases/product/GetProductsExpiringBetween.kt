package com.mohmmed.mosa.eg.towmmen.domin.usecases.product

import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.data.repository.ProductRepositoryImp
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class GetProductsExpiringBetween @Inject constructor(
    private val productRepositoryImp: ProductRepositoryImp
) {
    operator fun invoke(start: Date, end: Date): Flow<List<Product>>{
        return productRepositoryImp.getProductsExpiringBetween(start, end)
    }
}