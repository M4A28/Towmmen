package com.mohmmed.mosa.eg.towmmen.presenter.invoic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceWithItems
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ConfirmationDialog
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import com.mohmmed.mosa.eg.towmmen.util.dateToString
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency
import java.util.Locale


@Composable
fun InvoiceScreen(){
    // todo refactor this
    val invoiceViewModel: InvoiceViewModel = hiltViewModel()
    val productsViewModel: ProductViewModel = hiltViewModel()
    val invoiceWithItem by invoiceViewModel.getAllInvoicesWithItems().collectAsState(initial = emptyList())

    val allProducts by productsViewModel.products.collectAsState(initial = emptyList())
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

@Composable
fun InvoiceCard(invoice: Invoice,
                invoiceItems: List<InvoiceItem>,
                onEditClick: (Invoice) -> Unit = {},
                onDeleteClick: (Invoice) -> Unit = {}
) {

    var showItems by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .clickable { showItems = !showItems },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.invoice),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = invoice.invoiceId,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(id = R.string.invoice_date,
                    dateToString(invoice.date,
                        pattern = "yyyy MMMM dd",
                        locale = Locale.getDefault())) ,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = invoice.customerName,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(id = R.string.total, invoice.totalAmount),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            if(showItems){

                invoiceItems.forEach{ item ->
                    ListItem(
                        headlineContent = { Text(item.productName) },
                        supportingContent = { Text(stringResource(id = R.string.quantity, item.quantity)) },
                        trailingContent = { Text(stringResource(id =R.string.price_, item.unitPrice )) }
                    )
                    HorizontalDivider()
                }
            }

            Spacer(modifier = Modifier.height(6.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onEditClick(invoice) }) {
                    Icon(
                        painter = painterResource(R.drawable.edit),
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                IconButton(onClick = { onDeleteClick(invoice) }) {
                    Icon(
                        painter = painterResource(R.drawable.delete),
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

        }
    }
}



@Composable
fun InvoiceSummaryCard(invoice: Invoice) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface,)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                stringResource(R.string.invoice_number, invoice.invoiceId),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                stringResource(R.string.name, invoice.customerName),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                stringResource(id = R.string.total, formatCurrency(invoice.totalAmount) ),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}



