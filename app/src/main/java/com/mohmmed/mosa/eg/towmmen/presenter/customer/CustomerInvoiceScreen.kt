package com.mohmmed.mosa.eg.towmmen.presenter.customer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceWithItems
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ConfirmationDialog
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.invoic.InvoiceCard
import com.mohmmed.mosa.eg.towmmen.presenter.invoic.InvoiceViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import com.mohmmed.mosa.eg.towmmen.util.CUSTOMER_ID


@Composable
fun CustomerInvoiceScreen(navController: NavHostController){
    val invoiceViewModel: InvoiceViewModel = hiltViewModel()
    var showDeleteDialog by remember { mutableStateOf(false) }
    val productsViewModel: ProductViewModel = hiltViewModel()
    val allProducts by productsViewModel.products.collectAsState(initial = emptyList())
    var invoice by remember{ mutableStateOf<Invoice?>(null) }
    var invoiceWithItems by remember{ mutableStateOf<InvoiceWithItems?>(null) }

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
                         // todo
                },
                onDelete = {
                        items ->
                    invoiceWithItems = items
                    invoice = items.invoice
                    showDeleteDialog = !showDeleteDialog
                }
            )
        }
    if(showDeleteDialog) {
        ConfirmationDialog(
            title = stringResource(R.string.delet_invoice),
            text = stringResource(R.string.invoice_delete_warring),
            dismissText = stringResource(id = R.string.cancel),
            confirmText = stringResource(id = R.string.delete),
            onConfirm = {
                invoiceWithItems?.let{
                    it.items.forEach { item ->
                        val product = allProducts.first { it.productId == item.productId }
                        product.stockQuantity += item.quantity
                        productsViewModel.updateProduct(product)
                    }
                    invoiceViewModel.deleteInvoice(it.invoice)

                }
                showDeleteDialog = !showDeleteDialog
            },
            onDismiss = { showDeleteDialog = !showDeleteDialog }
        )
    }


}

@Composable
fun CustomerInvoiceContent(
    modifier: Modifier = Modifier,
    invoices: List<InvoiceWithItems>,
    onEdit: (InvoiceWithItems) -> Unit,
    onDelete: (InvoiceWithItems) -> Unit
){
    if(invoices.isNotEmpty()){
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {

            items(invoices,
                key = { it.invoice.invoiceId }
            ){ invoiceWithItems ->
                InvoiceCard(
                    invoice = invoiceWithItems.invoice,
                    invoiceItems = invoiceWithItems.items,
                    onDeleteClick ={ onDelete(invoiceWithItems)},
                    onEditClick = {onEdit(invoiceWithItems) }
                )
            }

        }
    }else{
        EmptyScreen(massage = stringResource(R.string.no_invoice_for_this_customer),
            icon = R.drawable.description ,
            alphaAnim = 0.3f)
    }
}


