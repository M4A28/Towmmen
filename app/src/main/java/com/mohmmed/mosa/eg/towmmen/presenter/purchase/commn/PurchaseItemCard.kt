package com.mohmmed.mosa.eg.towmmen.presenter.purchase.commn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product

@Composable
fun PurchaseItemCard(
    modifier: Modifier = Modifier,
    product: Product
) {

    var quantity by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("${product.cost}") }
    var price by remember { mutableStateOf("${product.price}") }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp,
                horizontal = 8.dp)
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text(stringResource(id = R.string.quantity_)) },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = cost,
                    onValueChange = { cost = it },
                    label = { Text(stringResource(id = R.string.cost_)) },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text(stringResource(id = R.string.price)) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }



}