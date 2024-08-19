package com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting

enum class AppTheme {

    LIGHT, DARK, SYSTEM


}

fun AppTheme.getRevers(): AppTheme{
    return if(this.name == AppTheme.LIGHT.name) AppTheme.LIGHT
    else AppTheme.DARK
}