package com.mohmmed.mosa.eg.towmmen.presenter.customer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mohmmed.mosa.eg.towmmen.domin.module.Customer
import com.mohmmed.mosa.eg.towmmen.presenter.comman.CustomerCard


@Composable
fun CustomerScreen(
    modifier: Modifier = Modifier,
    customers: List<Customer>,
    onCustomerClick: (Customer) -> Unit
){
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(customers.size){customer ->
            CustomerCard(
                customer = customers[customer],
                onClick = {onCustomerClick(customers[customer])}
            )
        }

    }

}