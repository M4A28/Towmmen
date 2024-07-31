package com.mohmmed.mosa.eg.towmmen.presenter.customer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.presenter.comman.CustomTextFiled
import com.mohmmed.mosa.eg.towmmen.util.CUSTOMER_KEY

@Composable
fun EditCustomerScreen(navController: NavHostController){
    val customerViewModel: CustomerViewModel = hiltViewModel()
    navController
        .previousBackStackEntry
        ?.savedStateHandle
        ?.get<Customer?>(CUSTOMER_KEY)?.let { customer ->
            EditCustomerContent(
                customer = customer,
                onSaveClick = {
                    customerViewModel.updateCustomer(it)
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCustomerContent(
    customer: Customer?,
    modifier: Modifier = Modifier,
    onSaveClick: (Customer) -> Unit,
    onBackClick: () -> Unit = {},
){

    var name by remember { mutableStateOf(customer?.name ?: "") }
    var email by remember { mutableStateOf(customer?.email ?: "") }
    var phone by remember { mutableStateOf(customer?.phone ?: "") }
    var address by remember { mutableStateOf(customer?.address ?: "") }


    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClick()
                    }){
                        Icon(
                            tint = MaterialTheme.colorScheme.onPrimary,
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "ArrowBack"
                        )
                    }
                },
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(id = R.string.edit_customer))
                    }

                })
        }
    ){ padding ->
        val topPadding = padding.calculateTopPadding()
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = topPadding, end = 4.dp, start = 4.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomTextFiled(
                value = name,
                onValueChange = { name = it },
                label = stringResource(id = R.string.customer_name_),
                leadingIcon = R.drawable.person
            )

            Spacer(modifier = Modifier.height(4.dp))

            CustomTextFiled(
                value = email,
                onValueChange = { email = it },
                label = stringResource(id = R.string.email),
                leadingIcon = R.drawable.email
            )
            Spacer(modifier = Modifier.height(4.dp))

            CustomTextFiled(
                value = phone,
                onValueChange = { phone = it },
                label = stringResource(id = R.string.phone),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                leadingIcon = R.drawable.phone
            )
            Spacer(modifier = Modifier.height(4.dp))

            CustomTextFiled(
                value = address,
                onValueChange = { address = it },
                label = stringResource(id = R.string.address),
                leadingIcon = R.drawable.location_pin
            )
            Spacer(modifier = Modifier.height(4.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if(name.isNotEmpty()){
                        if (customer != null) {
                            onSaveClick(
                                Customer(
                                    customerId = customer.customerId,
                                    name = name.trim(),
                                    email = email.trim(),
                                    phone = phone.trim(),
                                    address = address.trim(),
                                    registrationDate = customer.registrationDate,
                                    lastPurchaseDate = customer.lastPurchaseDate
                                )
                            )
                        }
                        // clear data
                        name = ""
                        email = ""
                        phone = ""
                        address = ""
                    }
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Customer")
                Text(stringResource(id = R.string.add_new_customer), modifier = Modifier.padding(start = 8.dp))
            }
        }
    }

}