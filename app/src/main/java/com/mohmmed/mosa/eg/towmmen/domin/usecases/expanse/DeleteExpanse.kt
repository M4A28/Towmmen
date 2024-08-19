package com.mohmmed.mosa.eg.towmmen.domin.usecases.expanse

import com.mohmmed.mosa.eg.towmmen.data.module.Expanse
import com.mohmmed.mosa.eg.towmmen.data.repository.ExpanseRepositoryImp
import javax.inject.Inject

class DeleteExpanse @Inject constructor(
    private val expanseRepo: ExpanseRepositoryImp
) {
    suspend operator fun invoke(expanse: Expanse){
        expanseRepo.deleteExpanse(expanse)
    }
}