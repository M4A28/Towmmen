package com.mohmmed.mosa.eg.towmmen.presenter.invoic

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceWithItems
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ConfirmationDialog
import com.mohmmed.mosa.eg.towmmen.presenter.invoic.comman.InvoiceCard


@Composable
fun InvoiceScreen(){
    // todo refactor this
    val invoiceViewModel: InvoiceViewModel = hiltViewModel()
    val invoiceWithItem by invoiceViewModel.getAllInvoicesWithItems().collectAsState(initial = emptyList())
    val context = LocalContext.current
    val allProducts by invoiceViewModel.products.collectAsState(initial = emptyList())
    var showDeleteDialog by remember { mutableStateOf(false) }
    var invoice by remember{ mutableStateOf<Invoice?>(null) }
    var invoiceWithItems_ by remember{ mutableStateOf<InvoiceWithItems?>(null) }

    InvoiceContent(
        invoiceWithItem = invoiceWithItem,
        onDelete = {invoiceWithItems ->
            invoiceWithItems_ = invoiceWithItems
            invoice = invoiceWithItems.invoice
            showDeleteDialog = !showDeleteDialog
                   },
        onEdit = { // todo
             },
    )
    if(showDeleteDialog){
        ConfirmationDialog(
            title = stringResource(R.string.delet_invoice),
            text = stringResource(R.string.invoice_delete_warring),
            dismissText = stringResource(id = R.string.cancel),
            confirmText = stringResource(id = R.string.delete),
            onConfirm = {
                invoiceWithItems_?.let{
                    it.items.forEach { item ->
                        val product = allProducts.firstOrNull { it.productId == item.productId }
                        if(product != null){
                            product.stockQuantity += item.quantity
                            invoiceViewModel.updateProduct(product)

                        }
                    }
                    invoiceViewModel.deleteInvoice(it.invoice)

                }
                showDeleteDialog = !showDeleteDialog
                Toast.makeText(context,
                    context.getString(R.string.invoice_deleted),
                    Toast.LENGTH_SHORT).show()
            },
            onDismiss = { showDeleteDialog = !showDeleteDialog }
    )
}
}

@Composable
fun InvoiceContent(
    modifier: Modifier = Modifier,
    invoiceWithItem: List<InvoiceWithItems>,
    onDelete:(InvoiceWithItems) -> Unit,
    onEdit :(InvoiceWithItems) -> Unit
){
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(invoiceWithItem,
            key = {
            it.invoice.invoiceId
        } ){ invoiceWithItems->

            InvoiceCard(
                invoice = invoiceWithItems.invoice,
                invoiceItems = invoiceWithItems.items,
                onDeleteClick ={onDelete(invoiceWithItems)},
                onEditClick = {onEdit(invoiceWithItems)})
        }
    }

}
