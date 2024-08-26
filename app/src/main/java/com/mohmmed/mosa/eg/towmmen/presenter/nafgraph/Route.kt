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
    object DealersScreen: Route(route = "dealersScreen")
    object OnBoardingScreen: Route(route = "onBoardingScreen")

    // secondary screens
    object AddProductScreen: Route(route = "addProductScreen")
    object CustomerInvoiceScreen: Route(route = "customerInvoiceScreen")
    object AddCustomerScreen: Route(route = "addCustomerScreen")
    object AddNoteScreen: Route(route = "addNoteScreen")
    object DealerFullInfoScreen: Route(route = "dealerFullInfoScreen")
    object DealerPurchasesScreen: Route(route = "dealerPurchasesScreen")
    object DealerEditInfoScreen: Route(route = "dealerEditInfoScreen")
    object EditLockerScreen: Route(route = "editLockerScreen")
    object AddLockerScreen: Route(route = "addLockerScreen")
    object LockerScreen: Route(route = "lockerScreen")

    object AddCategoryScreen: Route(route = "addCategoryScreen")
    object AddExpanseScreen: Route(route = "addExpanseScreen")
    object EditExpanseScreen: Route(route = "editExpanseScreen")
    object ProductFullInfoScreen: Route(route = "productFullInfoScreen")
    object EditProductScreen: Route(route = "editProductScreen")
    object EditCustomerScreen: Route(route = "editCustomerScreen")
    object EditNoteScreen: Route(route = "editNoteScreen")
    object CustomerFullInfoScreen: Route(route = "customerFullInfoScreen")
    object BarcodeScannerScreen: Route(route = "barcodeScannerScreen")
    object EmployeeFullInfoScreen: Route(route = "employeeFullInfoScreen")
    object SettingScreen: Route(route = "settingScreen")
    object AddInvoiceScreen: Route(route = "addInvoiceScreen")
    object InvoiceScreen: Route(route = "invoiceScreen")

    object ProfitScreen: Route(route = "profitScreen")
    object AboutScreen: Route(route = "aboutScreen")
    object CategoryScreen: Route(route = "categoryScreen")
    object NotificationScreen: Route(route = "notificationScreen")
    object AppNavigation: Route(route = "appNavigation")
    object AppStartNavigation: Route(route = "appStartNavigation")
    object AppNavigationScreen: Route(route = "appNavigationScreen")
    object NavigationScreen: Route(route = "navigationScreen")
}
