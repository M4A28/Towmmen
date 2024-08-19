package com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse

import com.mohmmed.mosa.eg.towmmen.data.module.ExpansePerMonth
import com.mohmmed.mosa.eg.towmmen.data.repository.ExpanseRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpansePerMonth @Inject constructor(
    private val expanseRepo: ExpanseRepositoryImp
) {
    operator fun invoke(): Flow<List<ExpansePerMonth>> {
        return expanseRepo.getExpansePerMonth()
    }
}