package com.mohmmed.mosa.eg.towmmen.presenter.test.drawer

enum class DrawerState {
    Opened,
    Closed
}

fun DrawerState.isOpened(): Boolean {
    return this.name == "Opened"
}

fun DrawerState.opposite(): DrawerState {
    return if (this == DrawerState.Opened) DrawerState.Closed
    else DrawerState.Opened
}