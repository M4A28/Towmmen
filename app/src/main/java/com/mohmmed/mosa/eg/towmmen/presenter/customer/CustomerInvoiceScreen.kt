package com.mohmmed.mosa.eg.towmmen.presenter.customer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceWithItems
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.invoic.InvoiceCard
import com.mohmmed.mosa.eg.towmmen.presenter.invoic.InvoiceViewModel
import com.mohmmed.mosa.eg.towmmen.util.CUSTOMER_ID


@Composable
fun CustomerInvoiceScreen(navController: NavHostController){
    val invoiceViewModel: InvoiceViewModel = hiltViewModel()

    navController
        .previousBackStackEntry
        ?.savedStateHandle
        ?.get<Int?>(CUSTOMER_ID)?.let {
            val invoices by invoiceViewModel
                .getInvoicesWithItemsByCustomerId(it)
                .collectAsState(initial = emptyList())
            CustomerInvoiceContent(
                invoices = invoices,
                onEdit = {

                },
                onDelete = {

                }
            )


        }


}

@Composable
fun CustomerInvoiceContent(
    modifier: Modifier = Modifier,
    invoices: List<InvoiceWithItems>,
    onEdit: (Invoice) -> Unit,
    onDelete: (Invoice) -> Unit
){
    if(invoices.isNotEmpty()){
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {

            items(invoices,
                key = { it.invoice.invoiceId }
            ){
                InvoiceCard(
                    invoice = it.invoice,
                    invoiceItems = it.items,
                    onDeleteClick ={ delete -> onDelete(delete) },
                    onEditClick = {edit -> onEdit(edit) }
                )
            }

            }
    }else{
        EmptyScreen(massage = stringResource(R.string.no_invoice_for_this_customer), icon = R.drawable.description , alphaAnim = 0.3f)
    }
}


