package com.mohmmed.mosa.eg.towmmen.data.localusermanger

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mohmmed.mosa.eg.towmmen.domin.localusermanger.ThemeRepository
import com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemeRepositoryImp @Inject constructor(
    private val dataStore: DataStore<Preferences>
): ThemeRepository {
    private val themeKey = stringPreferencesKey("theme")
    override fun getAppTheme(): Flow<AppTheme> {
        return dataStore.data.map { preferences ->
            AppTheme.valueOf(preferences[themeKey] ?: AppTheme.SYSTEM.name)
        }
    }

    override suspend fun setAppTheme(theme: AppTheme) {
        dataStore.edit { preferences ->
            preferences[themeKey] = theme.name
        }
    }
}