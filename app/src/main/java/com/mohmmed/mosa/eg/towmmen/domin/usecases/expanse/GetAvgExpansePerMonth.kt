package com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse

import com.mohmmed.mosa.eg.towmmen.data.repository.ExpanseRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAvgExpansePerMonth @Inject constructor(
    private val expanseRepository: ExpanseRepositoryImp
){

    operator fun invoke(): Flow<Double?> {
        return expanseRepository.getAvgExpansePerMonth()
    }
}