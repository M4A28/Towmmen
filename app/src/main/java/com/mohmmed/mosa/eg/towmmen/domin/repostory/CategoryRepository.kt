package com.mohmmed.mosa.eg.towmmen.domin.repostory

import com.mohmmed.mosa.eg.towmmen.domin.module.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun upsertCategory(category: Category)
    suspend fun deleteCategory(category: Category)
    fun getAllCategory(): Flow<List<Category>>
}