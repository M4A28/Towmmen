package com.mohmmed.mosa.eg.towmmen.di

import android.app.Application
import androidx.room.Room
import com.mohmmed.mosa.eg.towmmen.data.local.converters.DateTypeConverter
import com.mohmmed.mosa.eg.towmmen.data.local.db.TowmmenDatabase
import com.mohmmed.mosa.eg.towmmen.data.local.db.migration.migration_5_6
import com.mohmmed.mosa.eg.towmmen.data.local.db.migration.migration_6_7
import com.mohmmed.mosa.eg.towmmen.data.local.db.migration.migration_7_8

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
    fun provideTowmmenDatabase(
        application: Application
    ): TowmmenDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = TowmmenDatabase::class.java,
            name = "towwmen_db")
            .addTypeConverter(DateTypeConverter())
            .addMigrations(migration_5_6, migration_6_7, migration_7_8)
            .fallbackToDestructiveMigration()
            .build()
    }

}