package com.mohmmed.mosa.eg.towmmen.presenter.drawer.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.domin.module.Category
import com.mohmmed.mosa.eg.towmmen.domin.usecases.category.CategoryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases
): ViewModel(){
    fun upsertCategory(category: Category){
        viewModelScope.launch {
            categoryUseCases.upsertCategory(category)
        }
    }
    fun deleteCategory(category: Category){
        viewModelScope.launch {
            categoryUseCases.deleteCategory(category)
        }
    }
    fun getAllCategory(): Flow<List<Category>>{
        return categoryUseCases.getAllCategory()
    }
}