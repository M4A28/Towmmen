package com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse

import com.mohmmed.mosa.eg.towmmen.data.module.ExpansePerPeriod
import com.mohmmed.mosa.eg.towmmen.data.repository.ExpanseRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpansePerMonth @Inject constructor(
    private val expanseRepo: ExpanseRepositoryImp
) {
    operator fun invoke(): Flow<List<ExpansePerPeriod>> {
        return expanseRepo.getExpansePerMonth()
    }
}

class GetExpansePerDay @Inject constructor(
    private val expanseRepo: ExpanseRepositoryImp
) {
    operator fun invoke(): Flow<List<ExpansePerPeriod>> {
        return expanseRepo.getExpansePerDay()
    }
}

class GetExpansePerWeek @Inject constructor(
    private val expanseRepo: ExpanseRepositoryImp
) {
    operator fun invoke(): Flow<List<ExpansePerPeriod>> {
        return expanseRepo.getExpansePerWeek()
    }
}