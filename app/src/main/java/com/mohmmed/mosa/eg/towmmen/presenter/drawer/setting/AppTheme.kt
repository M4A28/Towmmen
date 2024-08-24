package com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting

enum class AppTheme {

    LIGHT, DARK, SYSTEM


}

fun AppTheme.getRevers(): AppTheme{
    return if(this.name == AppTheme.LIGHT.name) AppTheme.LIGHT
    else AppTheme.DARK
}


fun AppTheme.revers(): AppTheme{
    return if(AppTheme.LIGHT.name == this.name) AppTheme.DARK
    else AppTheme.LIGHT
}