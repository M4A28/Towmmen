package com.mohmmed.mosa.eg.towmmen.presenter.tab

sealed class TapItem(
    val name: String,
){
    object Home: TapItem(name = "Home")
    object Expanse: TapItem(name = "Home")
    object Invoice: TapItem(name = "Home")
}
