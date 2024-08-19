package com.mohmmed.mosa.eg.towmmen.presenter.navigator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.barcode.BarcodeScannerScreen
import com.mohmmed.mosa.eg.towmmen.presenter.customer.AddNewCustomerScreen
import com.mohmmed.mosa.eg.towmmen.presenter.customer.CustomerInvoiceScreen
import com.mohmmed.mosa.eg.towmmen.presenter.customer.CustomerScreen
import com.mohmmed.mosa.eg.towmmen.presenter.customer.EditCustomerScreen
import com.mohmmed.mosa.eg.towmmen.presenter.customer.FullCustomerInfoScreen
import com.mohmmed.mosa.eg.towmmen.presenter.dealers.DealersScreen
import com.mohmmed.mosa.eg.towmmen.presenter.dealers.FullDealerInfoScreen
import com.mohmmed.mosa.eg.towmmen.presenter.drawer.DrawerScreen
import com.mohmmed.mosa.eg.towmmen.presenter.drawer.about.AboutScreen
import com.mohmmed.mosa.eg.towmmen.presenter.drawer.category.AddCategoryScreen
import com.mohmmed.mosa.eg.towmmen.presenter.drawer.category.CategoryScreen
import com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting.SettingsScreen
import com.mohmmed.mosa.eg.towmmen.presenter.expanse.AddExpanseScreen
import com.mohmmed.mosa.eg.towmmen.presenter.home.HomeScreen
import com.mohmmed.mosa.eg.towmmen.presenter.invoic.AddInvoiceScreen
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.Route
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.componet.AppBottomNavigation
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.componet.BottomNavigationItem
import com.mohmmed.mosa.eg.towmmen.presenter.note.AddNoteScreen
import com.mohmmed.mosa.eg.towmmen.presenter.note.EditNoteScreen
import com.mohmmed.mosa.eg.towmmen.presenter.note.NoteScreen
import com.mohmmed.mosa.eg.towmmen.presenter.note.NoteViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.notifications.NotificationScreen
import com.mohmmed.mosa.eg.towmmen.presenter.product.AddNewProductScreen
import com.mohmmed.mosa.eg.towmmen.presenter.product.EditProductScreen
import com.mohmmed.mosa.eg.towmmen.presenter.product.FullProductInfoScreen
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductsScreen
import com.mohmmed.mosa.eg.towmmen.presenter.profit.ProfitScreen
import com.mohmmed.mosa.eg.towmmen.util.CUSTOMER_KEY
import com.mohmmed.mosa.eg.towmmen.util.PRODUCT_KEY
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigator() {

    val items = listOf(
        BottomNavigationItem(icon = R.drawable.home, text = R.string.home), // 0
        BottomNavigationItem(icon = R.drawable.person, text = R.string.customers), // 1
        BottomNavigationItem(icon = R.drawable.shopping_cart, text = R.string.products), // 2
        BottomNavigationItem(icon = R.drawable.delars, text = R.string.dealers), // 3
    )

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.CustomerScreen.route -> 1
            Route.ProductScreen.route -> 2
            Route.DealersScreen.route -> 3
            else -> -1
        }
    }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerScreen(navController)},
        content = {
            Scaffold(
                bottomBar = {
                    if(selectedItem == 0 ||
                        selectedItem == 1 ||
                        selectedItem == 2 ||
                        selectedItem == 3){
                        AppBottomNavigation(
                            items = items,
                            selected = selectedItem,
                            onClick = { index ->
                                when(index){
                                    0 -> navigateToTab(navController, Route.HomeScreen.route)
                                    1 -> navigateToTab(navController, Route.CustomerScreen.route)
                                    2 -> navigateToTab(navController, Route.ProductScreen.route)
                                    3 -> navigateToTab(navController, Route.DealersScreen.route)
                                }
                            }
                        )
                    }
                },
                topBar = {
                    if(selectedItem == 0) {

                        TopAppBar(
                         /*   colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            ),*/
                            title = {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.app_name)
                                    )

                                }
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }){
                                    Icon(
                                        //tint = MaterialTheme.colorScheme.onPrimary,
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Menu"
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = {
                                    navigateToTab(navController, Route.NotificationScreen.route)
                                }){
                                    Icon(
                                        painter = painterResource(id = R.drawable.notifications),
                                        contentDescription = "notifications"
                                    )
                                }
                            })
                    }
                },

                ){ innerPadding ->

                NavHost(modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding()),
                    navController = navController,
                    startDestination = Route.HomeScreen.route
                ) {


                    composable(route = Route.HomeScreen.route){
                        HomeScreen()
                    }

                    composable(route = Route.NotificationScreen.route){
                        NotificationScreen(navyControl = navController)
                    }

                    composable(route = Route.CustomerScreen.route){
                        CustomerScreen(navController)
                    }

                    composable(route = Route.DealersScreen.route){
                        DealersScreen(navController)
                    }

                    composable(route = Route.DealerFullInfoScreen.route){
                        FullDealerInfoScreen(navController)
                    }


                    composable(route = Route.ProductFullInfoScreen.route){
                        FullProductInfoScreen(navController)
                    }

                    composable(route = Route.EditCustomerScreen.route){
                        EditCustomerScreen(navController)
                    }

                    composable(route = Route.EditProductScreen.route){
                        EditProductScreen(navController)
                    }

                    composable(route = Route.CustomerInvoiceScreen.route){
                        CustomerInvoiceScreen(navController)
                    }

                    composable(route = Route.CustomerFullInfoScreen.route){
                        FullCustomerInfoScreen(navController)
                    }

                    composable(route = Route.EditNoteScreen.route){
                        EditNoteScreen(navController)
                    }


                    composable(route = Route.ProductScreen.route){
                        ProductsScreen(
                            onProductClick = { product -> navigateToProductDetails(navController, product) },
                            onFapClick = {
                                navigateToScreen(navController, Route.AddProductScreen.route)
                            }
                        )
                    }
                    composable(route = Route.AddProductScreen.route){
                        AddNewProductScreen(navController)
                    }
                    composable(route = Route.BarcodeScannerScreen.route){
                        BarcodeScannerScreen(navController)
                    }

                    composable(route = Route.AddCustomerScreen.route){
                        AddNewCustomerScreen()
                    }
                    // Todo Enhanc this
                    composable(route = Route.AddNoteScreen.route){
                        val noteViewModel: NoteViewModel = hiltViewModel()
                        AddNoteScreen(
                            onAddClick = { note -> noteViewModel.addtNote(note) }
                        )
                    }

                    composable(route = Route.NoteScreen.route){
                        NoteScreen(
                            navController = navController,
                            onFapClick = {
                                navigateToScreen(navController, Route.AddNoteScreen.route)
                            }

                        )
                    }


                    composable(route = Route.AboutScreen.route){
                        AboutScreen()
                    }

                    composable(route = Route.AddExpanseScreen.route){
                        AddExpanseScreen(navController)
                    }

                    composable(route = Route.ProfitScreen.route){
                        ProfitScreen(navController)
                    }

                    composable(route = Route.AddInvoiceScreen.route){
                        AddInvoiceScreen(navController)
                    }

                    composable(route = Route.CategoryScreen.route){
                        CategoryScreen(navController)
                    }
                    composable(route = Route.AddCategoryScreen.route){
                        AddCategoryScreen(navController)
                    }

                    composable(route = Route.SettingScreen.route){
                        SettingsScreen()
                    }

                }

            }
        }
    )

}

fun navigateToTab(navController: NavController, route: String){
    navController.navigate(route = route){
        navController.graph.startDestinationRoute?.let{
            popUpTo(it){ saveState = true }
            restoreState = true
            launchSingleTop = true
        }
    }
}

fun navigateToProductDetails(navController: NavController, product: Product){
    navController.currentBackStackEntry?.savedStateHandle?.set(PRODUCT_KEY, product)
    navController.navigate(Route.ProductFullInfoScreen.route)
}
fun navigateToCustomerDetails(navController: NavController, customer: Customer){
    navController.currentBackStackEntry?.savedStateHandle?.set(CUSTOMER_KEY, customer)
    navController.navigate(Route.CustomerFullInfoScreen.route)
}

fun navigateToScreen(navController: NavController, route: String){
    navController.navigate(route = route) {
        launchSingleTop = true
    }
}
