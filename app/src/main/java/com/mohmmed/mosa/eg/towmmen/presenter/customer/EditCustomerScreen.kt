package com.mohmmed.mosa.eg.towmmen.presenter.customer

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.util.CUSTOMER_KEY

@Composable
fun EditCustomerScreen(navController: NavHostController){
    val customerViewModel: CustomerViewModel = hiltViewModel()
    val context = LocalContext.current
    navController
        .previousBackStackEntry
        ?.savedStateHandle
        ?.get<Customer?>(CUSTOMER_KEY)?.let { customer ->
            EditCustomerContent(
                customer = customer,
                onSaveClick = {
                    customerViewModel.updateCustomer(it)
                    navController.navigateUp()
                    Toast.makeText(context,
                        context.getString(R.string.customer_data_edit), Toast.LENGTH_LONG).show()
                },
                onBackClick = {
                    navController.navigateUp()
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

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.person),
                        contentDescription = null ,
                        modifier = Modifier
                            .size(20.dp)
                    )
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.customer_name_))
                }
            )
            Spacer(modifier = Modifier.height(10.dp))


            TextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                value = email,
                onValueChange = { email = it },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.email),
                        contentDescription = null ,
                        modifier = Modifier
                            .size(20.dp)
                    )
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.email))
                }
            )

            Spacer(modifier = Modifier.height(10.dp))


            TextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                value = phone,
                onValueChange = { phone = it },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.phone),
                        contentDescription = null ,
                        modifier = Modifier
                            .size(20.dp)
                    )
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.phone))
                }
            )
            Spacer(modifier = Modifier.height(10.dp))


            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = address,
                onValueChange = { address = it },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.location_pin),
                        contentDescription = null ,
                        modifier = Modifier
                            .size(20.dp)
                    )
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.address))
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

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
                Icon(Icons.Filled.Add, contentDescription = "edit Customer")
                Text(stringResource(id = R.string.edit_customer),
                    modifier = Modifier.padding(start = 8.dp))
            }
        }
    }

}