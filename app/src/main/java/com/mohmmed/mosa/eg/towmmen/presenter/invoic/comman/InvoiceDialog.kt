package com.mohmmed.mosa.eg.towmmen.presenter.invoic.comman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency

@Composable
fun InvoiceDialog(invoice: Invoice,
                  invoiceItems: List<InvoiceItem>,
                  onDismiss: () -> Unit,
                  onConform: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 5.dp
        ) {
            LazyColumn(modifier = Modifier.padding(24.dp)) {
                item{
                    Text(
                        text = stringResource(R.string.invoice_number, invoice.invoiceId),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.name, invoice.customerName),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(id = R.string.total, formatCurrency(invoice.totalAmount) ),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    ) 
                    Text(
                        text = stringResource(id = R.string.profit_, formatCurrency(invoice.profit) ),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
                items(invoiceItems){item ->
                    ListItem(
                        headlineContent = { Text(item.productName) },
                        supportingContent = { Text(stringResource(id = R.string.quantity, item.quantity)) },
                        trailingContent = { Text(stringResource(id =R.string.price_, item.unitPrice )) }
                    )
                    HorizontalDivider()

                }

                item{
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween){

                        Button(onClick = onConform,) {
                            Text(stringResource(id = R.string.confirm))
                        }
                        TextButton(onClick = onDismiss,) {
                            Text(stringResource(id = R.string.close))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InvoiceItemsList(items: List<InvoiceItem>) {
    Text(
        "Items",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    LazyColumn {
        items(items.size) { item ->
            ListItem(
                headlineContent = { Text(items[item].productName) },
                supportingContent = { Text(stringResource(id = R.string.quantity, items[item].quantity)) },
                trailingContent = { Text(stringResource(id =R.string.price_, items[item].unitPrice )) }
            )
            HorizontalDivider()
        }
    }
}
