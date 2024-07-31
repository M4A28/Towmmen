package com.mohmmed.mosa.eg.towmmen.domin.usecases.category

import com.mohmmed.mosa.eg.towmmen.data.repository.CategoryRepositoryImp
import com.mohmmed.mosa.eg.towmmen.data.module.Category
import javax.inject.Inject

class DeleteCategory  @Inject constructor(
    private val categoryRepositoryImp: CategoryRepositoryImp
){
    suspend operator fun invoke(category: Category){
        categoryRepositoryImp.deleteCategory(category)
    }

}