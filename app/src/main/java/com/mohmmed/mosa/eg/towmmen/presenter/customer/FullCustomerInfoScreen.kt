package com.mohmmed.mosa.eg.towmmen.presenter.customer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.presenter.comman.DeleteConfirmationDialog
import com.mohmmed.mosa.eg.towmmen.presenter.comman.DetailItem
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.Route
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.navigateToTab
import com.mohmmed.mosa.eg.towmmen.ui.theme.CairoFont
import com.mohmmed.mosa.eg.towmmen.util.CUSTOMER_ID
import com.mohmmed.mosa.eg.towmmen.util.CUSTOMER_KEY
import com.mohmmed.mosa.eg.towmmen.util.dateToString
import kotlinx.coroutines.launch

@Composable
fun FullCustomerInfoScreen(navController: NavHostController) {
    val customerViewModel: CustomerViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Customer?>(CUSTOMER_KEY)?.let {
            val customer by customerViewModel.getCustomerById(it.customerId).collectAsState(initial = it)
            FullCustomerInfoContent(
                customer = customer,
                onDeleteClick = {
                    showDeleteConfirmation = true
                },
                onEditClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(CUSTOMER_KEY, customer)
                    navController.navigate(Route.EditCustomerScreen.route)
                },
                onPurchaseClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(CUSTOMER_KEY, customer)
                    navigateToTab(navController, Route.AddInvoiceScreen.route)
                    //navController.navigate(Route.AddInvoiceScreen.route)

                },
                onShowInvoicesClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(CUSTOMER_ID, customer.customerId)
                    navController.navigate(Route.CustomerInvoiceScreen.route)
                }
            )


            if (showDeleteConfirmation) {
                DeleteConfirmationDialog(
                    title = stringResource(id = R.string.are_you_sure_you_want_to_delete_this_product),
                    massage = stringResource(id = R.string.confirm_deletion),
                    onConfirm = {
                        coroutineScope.launch {
                            customerViewModel.deleteCustomer(customer)
                            navController.navigateUp()
                        }
                    },
                    onDismiss = { showDeleteConfirmation = false }
                )
            }
        }




}

@Composable
fun FullCustomerInfoContent(
    modifier: Modifier = Modifier,
    customer: Customer,
    onDeleteClick:  (Customer)  -> Unit,
    onEditClick: (Customer)  -> Unit,
    onPurchaseClick: (Customer) -> Unit = {},
    onShowInvoicesClick: (Customer) -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        CustomerHeader(customer,
            onEditClick = {onEditClick(customer)},
            onDeleteClick = { onDeleteClick(customer) })
        Spacer(modifier = Modifier.height(16.dp))
        CustomerDetails(customer)
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            Button(
                onClick = { onPurchaseClick(customer) },
                colors = ButtonDefaults
                    .buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.record_purchase),
                    fontFamily = CairoFont
                )
            }
            Button(
                onClick = {onShowInvoicesClick(customer)},
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Spacer(Modifier.width(8.dp))
                Text(text = stringResource(R.string.show_invoices),
                    fontFamily = CairoFont)
            }
        }
    }

}



@Composable
fun CustomerHeader(
    customer: Customer,
    onEditClick: (Customer) -> Unit,
    onDeleteClick: (Customer) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Icon(
            painter = painterResource(id = R.drawable.person),
            contentDescription = "Customer Image",
            //alpha = 0.4f,
            modifier = Modifier.fillMaxSize(),
            tint = MaterialTheme.colorScheme.secondary
        )

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp) //
        ) {
            FilledIconButton(
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.Unspecified
                ),
                onClick = { onEditClick(customer) }) {
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    painter = painterResource(id = R.drawable.edit ),
                    contentDescription = null
                )
            }

            Spacer(Modifier.width(32.dp))
            FilledIconButton(
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.Unspecified
                ),
                onClick = { onDeleteClick(customer) }) {
                Icon(
                    tint = MaterialTheme.colorScheme.error,
                    painter = painterResource(id = R.drawable.delete ),
                    contentDescription = null
                )
            }
        }

        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            fontFamily = CairoFont,
            fontWeight = FontWeight.Bold,
            text = customer.name,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun CustomerDetails(customer: Customer) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            DetailItem(icon = R.drawable.email, label = stringResource(id = R.string.email), value = customer.email)
            DetailItem(icon = R.drawable.phone, label = stringResource(id = R.string.phone), value = customer.phone)
            DetailItem(icon = R.drawable.location_pin, label = stringResource(id = R.string.address), value = customer.address)
            DetailItem(
                icon = R.drawable.calendar_month,
                label = stringResource(id = R.string.reg_data_2),
                value = dateToString(customer.registrationDate, "yyyy-MM-dd")
            )
        }
    }
}