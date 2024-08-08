package com.mohmmed.mosa.eg.towmmen.presenter.invoic

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceWithItems
import com.mohmmed.mosa.eg.towmmen.ui.theme.CairoFont
import com.mohmmed.mosa.eg.towmmen.util.dateToString
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency


@Composable
fun InvoiceScreen(){
    val invoiceViewModel: InvoiceViewModel = hiltViewModel()
    val invoiceWithItem by invoiceViewModel.getAllInvoicesWithItems().collectAsState(initial = emptyList())
    InvoiceContent(invoiceWithItem = invoiceWithItem)
}

@Composable
fun InvoiceContent(
    modifier: Modifier = Modifier,
    invoiceWithItem: List<InvoiceWithItems>
){
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(invoiceWithItem,
            key ={
            it.invoice.invoiceId
        } ){
            InvoiceCard(
                invoice = it.invoice,
                invoiceItems = it.items,
                onDeleteClick ={
                   // todo

            },
                onEditClick = {
                    // todo
                })
        }
    }

}







@Composable
fun InvoiceCard(invoice: Invoice,
                invoiceItems: List<InvoiceItem>,
                onEditClick: (Invoice) -> Unit = {},
                onDeleteClick: (Invoice) -> Unit = {}
) {

    val quantity by remember { mutableStateOf(5) }
    val elevation by animateFloatAsState(if (quantity > 1) 4f else 2f)
    var showItems by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { showItems = !showItems }
            .shadow(elevation.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            Text(
                text = stringResource(id = R.string.invoice_number, invoice.invoiceId) ,
                fontFamily = CairoFont,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = stringResource(id = R.string.invoice_date, dateToString(invoice.date, "yyyy-MM-dd hh:mm")) ,
                fontFamily = CairoFont,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = invoice.customerName,
                fontFamily = CairoFont,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(id = R.string.total, invoice.totalAmount),
                fontFamily = CairoFont,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
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



