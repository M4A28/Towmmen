package com.mohmmed.mosa.eg.towmmen.domin.usecases.app_theme

import com.mohmmed.mosa.eg.towmmen.data.localusermanger.ThemeRepositoryImp
import com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting.AppTheme
import javax.inject.Inject

class SetAppTheme @Inject constructor(
    private val themeRepositoryImp: ThemeRepositoryImp
){
    suspend operator fun invoke(theme: AppTheme) {
        themeRepositoryImp.setAppTheme(theme)
    }
}