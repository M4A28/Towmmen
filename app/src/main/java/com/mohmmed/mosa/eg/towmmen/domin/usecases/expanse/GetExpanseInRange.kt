package com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse

import com.mohmmed.mosa.eg.towmmen.data.module.Expanse
import com.mohmmed.mosa.eg.towmmen.data.repository.ExpanseRepositoryImp
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class GetExpanseInRange @Inject constructor(
    private val expanseRepo: ExpanseRepositoryImp
) {
    operator fun invoke(start: Date, end: Date): Flow<List<Expanse>>{
        return expanseRepo.getExpanseInRange(start, end)
    }
}