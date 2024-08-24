package com.mohmmed.mosa.eg.towmmen.domin.usecases.category

import com.mohmmed.mosa.eg.towmmen.data.repository.CategoryRepositoryImp
import javax.inject.Inject

class ClearCategory @Inject constructor(
    private val categoryRepositoryImp: CategoryRepositoryImp
){
    suspend operator fun invoke(){
        categoryRepositoryImp.clearCategoryDate()
    }

}