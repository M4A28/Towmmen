package com.mohmmed.mosa.eg.towmmen.presenter.customer

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.presenter.comman.CustomerCard
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ModernSearchBarWithSuggestions
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.Route
import com.mohmmed.mosa.eg.towmmen.util.CUSTOMER_KEY


@Composable
fun CustomerScreen(navController: NavHostController){
    val customerViewModel: CustomerViewModel = hiltViewModel()
    val customers by customerViewModel
        .getAllCustomer()
        .collectAsState(initial = emptyList())
    var showAddCustomerDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    CustomerContent(
        customers = customers,
        onCustomerClick = {
            navController.currentBackStackEntry?.savedStateHandle?.set(CUSTOMER_KEY, it)
            navController.navigate(Route.CustomerFullInfoScreen.route)
        },
        onFapClick = { showAddCustomerDialog = true },
        onCallClick = {
            val phoneUri = Uri.fromParts("tel", it.phone, null)
            val intent = Intent(Intent.ACTION_DIAL, phoneUri)
            intent.also{
                if(it.resolveActivity(context.packageManager) != null){
                    context.startActivity(it)
                }
            }
        }
    )

    if(showAddCustomerDialog){
        AddCustomerDialog(onAddClick = {
            customerViewModel.addNewCustomer(it)
        }, showDialog = {showAddCustomerDialog = !showAddCustomerDialog})
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerContent(
    modifier: Modifier = Modifier,
    customers: List<Customer>,
    onCustomerClick: (Customer) -> Unit,
    onCallClick: (Customer) -> Unit,
    onFapClick: () -> Unit =  {}
){

    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(id = R.string.customers))
                    }

                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onFapClick()
                }
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }

        }
    ) { paddingValue ->
        if(customers.isNotEmpty()){
            LazyColumn(
                modifier = modifier.fillMaxSize(),
            ) {
                item {
                    ModernSearchBarWithSuggestions(
                        searchQuery = searchQuery,
                        onSearchQueryChange = { searchQuery = it },
                        suggestions = customers.map { it.name }.filter{ it.contains(searchQuery, ignoreCase = true) },
                        onSuggestionSelected = {searchQuery = it },
                        modifier = Modifier.padding(
                            top = paddingValue.calculateTopPadding(),
                            bottom = 10.dp,
                            start = 16.dp,
                            end = 16.dp )
                    )
                }

                items(
                    customers.filter {
                        it.name.contains(searchQuery, ignoreCase = true)
                    },
                    key = { it.customerId }
                ){ customer ->
                    CustomerCard(
                        customer = customer,
                        onClick = {onCustomerClick(customer)},
                        onPhoneClick = { onCallClick(customer) }
                    )
                }
            }
        }else{
            EmptyScreen(
                massage = stringResource(R.string.no_customers_yet),
                icon = R.drawable.person,
                alphaAnim = 0.6f
            )
        }

    }

}