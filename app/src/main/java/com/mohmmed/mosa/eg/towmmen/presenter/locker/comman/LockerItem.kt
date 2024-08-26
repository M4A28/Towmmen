package com.mohmmed.mosa.eg.towmmen.presenter.locker.comman

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Locker
import com.mohmmed.mosa.eg.towmmen.data.module.TransactionType
import com.mohmmed.mosa.eg.towmmen.util.dateToString
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency


@Composable
fun LockerItem(lockerItem: Locker) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = dateToString(lockerItem.transActionDate, "yyyy-MM-dd"),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = transActionTypeName(lockerItem.transActionType) ,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = lockerItem.transActionNote,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = formatCurrency(lockerItem.transActionAmount),
                style = MaterialTheme.typography.titleLarge,
                color = when (lockerItem.transActionType) {
                    TransactionType.ADD.name -> Color(0xFF4CAF50)
                    TransactionType.DISCOUNT.name -> Color(0xFFF44336)
                    TransactionType.BUY.name -> Color(0xFFFF9800)
                    TransactionType.SELL.name -> Color(0xFF2196F3)
                    else -> MaterialTheme.colorScheme.primary
                },
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun transActionTypeName(transaction: String): String {
    return when (transaction) {
        TransactionType.ADD.name -> stringResource(id = R.string.add)
        TransactionType.DISCOUNT.name -> stringResource(id = R.string.discount)
        TransactionType.BUY.name -> stringResource(id = R.string.buy)
        TransactionType.SELL.name -> stringResource(id = R.string.sell)

        else -> stringResource(id = R.string.undefined)
    }
}

