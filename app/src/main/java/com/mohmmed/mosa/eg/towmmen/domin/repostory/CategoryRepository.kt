package com.mohmmed.mosa.eg.towmmen.domin.repostory

import com.mohmmed.mosa.eg.towmmen.data.module.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun upsertCategory(category: Category)
    suspend fun deleteCategory(category: Category)
    fun getAllCategory(): Flow<List<Category>>
    suspend fun clearCategoryDate()
}