package com.mohmmed.mosa.eg.towmmen.di

import com.mohmmed.mosa.eg.towmmen.data.local.dao.CategoryDao
import com.mohmmed.mosa.eg.towmmen.data.local.db.TowmmenDatabase
import com.mohmmed.mosa.eg.towmmen.data.repository.CategoryRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.usecases.category.CategoryUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.category.ClearCategory
import com.mohmmed.mosa.eg.towmmen.domin.usecases.category.DeleteCategory
import com.mohmmed.mosa.eg.towmmen.domin.usecases.category.GetAllCategory
import com.mohmmed.mosa.eg.towmmen.domin.usecases.category.UpsertCategory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryModule {

    @Provides
    @Singleton
    fun provideCategoryDao(towwmenDB: TowmmenDatabase): CategoryDao{
        return towwmenDB.categoryDao
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepositoryImp {
        return CategoryRepositoryImp(categoryDao)
    }


    @Provides
    @Singleton
    fun provideCategoryUseCases(categoryRepositoryImp: CategoryRepositoryImp): CategoryUseCases {
        return CategoryUseCases(
            upsertCategory = UpsertCategory(categoryRepositoryImp),
            deleteCategory = DeleteCategory(categoryRepositoryImp),
            getAllCategory = GetAllCategory(categoryRepositoryImp),
            clearCategory = ClearCategory(categoryRepositoryImp)
        )
    }
}