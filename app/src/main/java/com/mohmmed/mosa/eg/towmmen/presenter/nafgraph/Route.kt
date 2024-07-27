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
    object AddCustomerScreen: Route(route = "addCustomerScreen")
    object AddNoteScreen: Route(route = "addNoteScreen")
    object AddCategoryScreen: Route(route = "addCategoryScreen")
    object ProductFullInfoScreen: Route(route = "productFullInfoScreen")
    object CustomerFullInfoScreen: Route(route = "customerFullInfoScreen")
    object EmployeeFullInfoScreen: Route(route = "employeeFullInfoScreen")
    object SettingScreen: Route(route = "settingScreen")
    object ProfitScreen: Route(route = "profitScreen")
    object AboutScreen: Route(route = "aboutScreen")
    object CategoryScreen: Route(route = "categoryScreen")
    object NotificationScreen: Route(route = "notificationScreen")
    object AppNavigation: Route(route = "appNavigation")
    object AppStartNavigation: Route(route = "appStartNavigation")
    object AppNavigationScreen: Route(route = "appNavigationScreen")
    object NavigationScreen: Route(route = "navigationScreen")
}
