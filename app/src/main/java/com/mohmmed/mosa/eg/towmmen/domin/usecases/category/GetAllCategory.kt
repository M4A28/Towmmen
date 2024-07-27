package com.mohmmed.mosa.eg.towmmen.domin.usecases.category

import com.mohmmed.mosa.eg.towmmen.data.repository.CategoryRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.module.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategory @Inject constructor(
    private val categoryRepositoryImp: CategoryRepositoryImp
){
    operator fun invoke(): Flow<List<Category>> {
        return categoryRepositoryImp.getAllCategory()
    }

}