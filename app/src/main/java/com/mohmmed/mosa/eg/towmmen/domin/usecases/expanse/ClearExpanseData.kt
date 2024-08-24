package com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse

import com.mohmmed.mosa.eg.towmmen.data.repository.ExpanseRepositoryImp
import javax.inject.Inject

class ClearExpanseData @Inject constructor(
    private val expanseRepo: ExpanseRepositoryImp
) {
    suspend operator fun invoke(){
        expanseRepo.clearExpanseDate()
    }
}