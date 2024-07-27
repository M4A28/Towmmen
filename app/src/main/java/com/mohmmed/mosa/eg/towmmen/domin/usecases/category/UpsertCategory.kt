package com.mohmmed.mosa.eg.towmmen.domin.usecases.category

import com.mohmmed.mosa.eg.towmmen.data.repository.CategoryRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.module.Category
import javax.inject.Inject

class UpsertCategory  @Inject constructor(
    private val categoryRepositoryImp: CategoryRepositoryImp
){
    suspend operator fun invoke(category: Category){
        categoryRepositoryImp.upsertCategory(category)
    }

}
