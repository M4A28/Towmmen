package com.mohmmed.mosa.eg.towmmen.domin.usecases.category

data class CategoryUseCases(
    val upsertCategory: UpsertCategory,
    val deleteCategory: DeleteCategory,
    val getAllCategory: GetAllCategory,
)
