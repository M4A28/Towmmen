package com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.domin.usecases.app_theme.AppThemeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AppThemeViewModel @Inject constructor(
    private val appThemeUseCases: AppThemeUseCases
): ViewModel() {
    val theme: StateFlow<AppTheme> = appThemeUseCases.getAppTheme()
        .stateIn(viewModelScope, SharingStarted.Eagerly, AppTheme.SYSTEM)

    fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            appThemeUseCases.setAppTheme(theme)
        }
    }


}