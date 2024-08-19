package com.mohmmed.mosa.eg.towmmen.domin.localusermanger

import com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting.AppTheme
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {

    fun getAppTheme(): Flow<AppTheme>

    suspend fun setAppTheme(theme: AppTheme)
}