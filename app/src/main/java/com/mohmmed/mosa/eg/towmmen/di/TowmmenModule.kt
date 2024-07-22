package com.mohmmed.mosa.eg.towmmen.di

import android.app.Application
import androidx.room.Room
import com.mohmmed.mosa.eg.towmmen.data.local.converters.DateTypeConverter
import com.mohmmed.mosa.eg.towmmen.data.local.dao.CustomerProductDao
import com.mohmmed.mosa.eg.towmmen.data.local.db.TowmmenDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TowmmenModule {




    @Provides
    @Singleton
    fun provideCustomerProductDao(towmmenDatabase: TowmmenDatabase): CustomerProductDao {
        return towmmenDatabase.customerProductDao
    }



    @Provides
    @Singleton
    fun provideTowmmenDatabase(
        application: Application
    ): TowmmenDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = TowmmenDatabase::class.java,
            name = "towwmen_db")
            .addTypeConverter(DateTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

}