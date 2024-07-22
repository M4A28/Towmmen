package com.mohmmed.mosa.eg.towmmen.presenter.nafgraph

sealed class Route(
    val route: String
)
{
    // main screens
    object HomeScreen: Route(route = "homeScreen")
    object CustomerScreen: Route(route = "customerScreen")
    object ProductScreen: Route(route = "productScreen")
    object NoteScreen: Route(route = "noteScreen")
    object OnBoardingScreen: Route(route = "onBoardingScreen")

    // secondary screens
    object AddProductScreen: Route(route = "addProductScreen")
    object AddNoteScreen: Route(route = "addNoteScreen")
    object NotificationScreen: Route(route = "notificationScreen")
    object AddCustomerScreen: Route(route = "addCustomerScreen")
    object AppNavigation: Route(route = "appNavigation")
    object AppStartNavigation: Route(route = "appStartNavigation")
    object AppNavigationScreen: Route(route = "appNavigationScreen")
    object NavigationScreen: Route(route = "navigationScreen")
}
