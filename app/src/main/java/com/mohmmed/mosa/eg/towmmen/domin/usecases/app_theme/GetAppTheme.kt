package com.mohmmed.mosa.eg.towmmen.domin.usecases.app_theme

import com.mohmmed.mosa.eg.towmmen.data.localusermanger.ThemeRepositoryImp
import com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting.AppTheme
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppTheme @Inject constructor(
    private val themeRepositoryImp: ThemeRepositoryImp
){
    operator fun invoke(): Flow<AppTheme> {
        return themeRepositoryImp.getAppTheme()
    }

}