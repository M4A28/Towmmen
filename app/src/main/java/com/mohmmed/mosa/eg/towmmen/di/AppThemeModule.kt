package com.mohmmed.mosa.eg.towmmen.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mohmmed.mosa.eg.towmmen.data.localusermanger.ThemeRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.usecases.app_theme.AppThemeUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.app_theme.GetAppTheme
import com.mohmmed.mosa.eg.towmmen.domin.usecases.app_theme.SetAppTheme
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppThemeModule {
    @Provides
    @Singleton
    fun provideAppThemeRepository(dataStore: DataStore<Preferences>): ThemeRepositoryImp {
        return ThemeRepositoryImp(dataStore = dataStore)
    }

    @Provides
    @Singleton
    fun provideAppThemeUseCases(themeRepositoryImp: ThemeRepositoryImp): AppThemeUseCases{
        return AppThemeUseCases(
            getAppTheme = GetAppTheme(themeRepositoryImp),
            setAppTheme = SetAppTheme(themeRepositoryImp)
        )
    }


}