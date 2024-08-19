package com.mohmmed.mosa.eg.towmmen.presenter.comman

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.data.module.Dealer


@Composable
fun DealerCard(
    modifier: Modifier = Modifier,
    dealer: Dealer,
    onPhoneClick: (Dealer) -> Unit = {},
    onClick: (Dealer)  -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(dealer) }
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = dealer.dealerName,
                style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = dealer.dealerAddress)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = dealer.dealerPhoneNumber)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { onPhoneClick(dealer) }) {
                    Icon(Icons.Default.Phone, contentDescription = "Contact")
                }
            }
        }
    }
}

