package com.mohmmed.mosa.eg.towmmen.domin.usecases.product

import com.mohmmed.mosa.eg.towmmen.data.module.MonthlyAvgPrice
import com.mohmmed.mosa.eg.towmmen.data.repository.ProductRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAveragePriceByMonth @Inject constructor(
    private val productRepository: ProductRepositoryImp
) {

    operator fun invoke(): Flow<List<MonthlyAvgPrice>> {
        return productRepository.getAveragePriceByMonth()
    }

}
