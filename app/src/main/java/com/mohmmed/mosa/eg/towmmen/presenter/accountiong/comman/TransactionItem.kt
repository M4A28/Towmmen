package com.mohmmed.mosa.eg.towmmen.presenter.accountiong.comman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.util.dateToString
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    invoice: Invoice

) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(invoice.invoiceId, fontWeight = FontWeight.Bold)
            Text(invoice.customerName, style = MaterialTheme.typography.bodySmall)
        }
        Column(horizontalAlignment = Alignment.End) {

            Text(formatCurrency(invoice.totalAmount), fontWeight = FontWeight.Bold)
            Text(dateToString(invoice.date, "yyyy-MM-dd"),
                style = MaterialTheme.typography.bodySmall)
        }
    }
}