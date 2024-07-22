package com.mohmmed.mosa.eg.towmmen.presenter.navigator

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.presenter.customer.AddNewCustomerScreen
import com.mohmmed.mosa.eg.towmmen.presenter.customer.CustomerScreen
import com.mohmmed.mosa.eg.towmmen.presenter.customer.CustomerViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.Route
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.componet.AppBottomNavigation
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.componet.BottomNavigationItem
import com.mohmmed.mosa.eg.towmmen.presenter.note.NoteScreen
import com.mohmmed.mosa.eg.towmmen.presenter.note.NoteViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.notifications.NotificationScreen
import com.mohmmed.mosa.eg.towmmen.presenter.product.AddNewProductScreen
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductsScreen
import com.mohmmed.mosa.eg.towmmen.presenter.test.EnhancedAWSInsertNoteUI
import com.mohmmed.mosa.eg.towmmen.util.ONE_DAY
import java.util.Date

@Composable
fun AppNavigator() {

    val items = listOf(
        BottomNavigationItem(icon = R.drawable.home, text = R.string.home),
        BottomNavigationItem(icon = R.drawable.person, text = R.string.customers),
        BottomNavigationItem(icon = R.drawable.shopping_cart, text = R.string.products),
        BottomNavigationItem(icon = R.drawable.notes, text = R.string.notes),
    )

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable { mutableStateOf(0) }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.CustomerScreen.route -> 1
            Route.ProductScreen.route -> 2
            Route.NoteScreen.route -> 3
            else -> 0
        }
    }

    Scaffold(
        bottomBar = {
            AppBottomNavigation(
                items = items,
                selected = selectedItem,
                onClick = { index ->
                    when(index){
                        0 -> navigateToTab(navController, Route.HomeScreen.route)
                        1 -> navigateToTab(navController, Route.CustomerScreen.route)
                        2 -> navigateToTab(navController, Route.ProductScreen.route)
                        3 -> navigateToTab(navController, Route.NoteScreen.route)
                    }
                }
            )
        },

        floatingActionButton = {
            if(selectedItem == 1
                || selectedItem == 2
                || selectedItem == 3){
                FloatingActionButton(
                    onClick = {
                        when(selectedItem){
                            1 -> navigateToTab(navController, Route.AddCustomerScreen.route)
                            2 -> navigateToTab(navController, Route.AddProductScreen.route)
                            3 -> navigateToTab(navController, Route.AddNoteScreen.route)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            }
        }
    ){ innerPadding ->

        NavHost(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            navController = navController,
            startDestination = Route.HomeScreen.route
        ) {
            composable(route = Route.HomeScreen.route){
                val productViewModel: ProductViewModel = hiltViewModel()
                val products by productViewModel
                    .products
                    .collectAsState(initial = emptyList())

                val exp = products.filter {
                    (it.expireDate.time - Date().time) >= (ONE_DAY * 30L)
                            || (it.expireDate.time - Date().time) <= 0
                }

                    NotificationScreen(
                        expProducts = exp
                    )


            }

            composable(route = Route.CustomerScreen.route){
                val customerViewModel: CustomerViewModel = hiltViewModel()
                val customers by customerViewModel
                    .getAllCustomer()
                    .collectAsState(initial = emptyList())
                CustomerScreen(
                    customers = customers,
                    onCustomerClick = {}
                )

            }

            composable(route = Route.ProductScreen.route){
                val productViewModel: ProductViewModel = hiltViewModel()
                val products by productViewModel
                    .products
                    .collectAsState(initial = emptyList())

                ProductsScreen(
                    products = products,
                    onProductClick = {}
                )

            }
            composable(route = Route.AddProductScreen.route){
                val productViewModel: ProductViewModel = hiltViewModel()
                AddNewProductScreen(onAddClick = {
                    productViewModel.addNewProduct(it)
                })


            }

            composable(route = Route.AddCustomerScreen.route){
                val customerViewModel: CustomerViewModel = hiltViewModel()
                AddNewCustomerScreen(onAddClick = {
                    customerViewModel.addNewCustomer(it)
                })

            }

            composable(route = Route.AddNoteScreen.route){
                val noteViewModel: NoteViewModel = hiltViewModel()
                EnhancedAWSInsertNoteUI(
                    onAddClick = { note ->
                        noteViewModel.addtNote(note)
                    }
                )

            }

            composable(route = Route.NoteScreen.route){

                val noteViewModel: NoteViewModel = hiltViewModel()
                val notes by noteViewModel
                    .getAllNote()
                    .collectAsState(initial = emptyList())
                NoteScreen(
                    notes = notes,
                    onEditClick = {},
                    onDeleteClick = {}
                )
            }

        }

    }

}

private fun navigateToTab(navController: NavController, route: String){
    navController.navigate(route = route){
        navController.graph.startDestinationRoute?.let{ home ->
            popUpTo(home){
                saveState = true
            }
            restoreState = true
            launchSingleTop = true

        }
    }
}