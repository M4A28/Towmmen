package com.mohmmed.mosa.eg.towmmen.presenter.customer

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.domin.module.Customer
import com.mohmmed.mosa.eg.towmmen.presenter.comman.CustomerCard
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ModernSearchBar
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.Route
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.navigateToAddTab


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CustomerScreen(navController: NavHostController,
                   onCustomerClick: (Customer) -> Unit){
    val customerViewModel: CustomerViewModel = hiltViewModel()
    val customers by customerViewModel
        .getAllCustomer()
        .collectAsState(initial = emptyList())
    val context = LocalContext.current

    CustomerContent(
        customers = customers,
        onCustomerClick = {
            onCustomerClick(it)
        },
        onFapClick = { navigateToAddTab(
            navController,
            Route.AddCustomerScreen.route,
            Route.CustomerScreen.route
        )
        },
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

}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp)),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),

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
                },
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(4.dp)
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }

        }
    ) {
        if(customers.isNotEmpty()){
            LazyColumn(
                modifier = modifier.fillMaxSize().padding(16.dp),
            ) {
                item {
                    ModernSearchBar(
                        searchQuery = searchQuery,
                        onSearchQueryChange = { searchQuery = it },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                items(customers.filter {
                    it.name.contains(searchQuery, ignoreCase = true)
                }
                    .size){customer ->
                    CustomerCard(
                        customer = customers[customer],
                        onClick = {onCustomerClick(customers[customer])},
                        onPhoneClick = {
                            onCallClick(customers[customer])
                        }
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