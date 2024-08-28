package com.mohmmed.mosa.eg.towmmen.presenter.purchase.commn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product

@Composable
fun PurchaseItemCard(
    modifier: Modifier = Modifier,
    product: Product,
    onCostChang: (Double) -> Unit = {},
    onPriceChang: (Double) -> Unit = {},
    onQuantityChang: (Int) -> Unit = {},
    onCloseClick: () -> Unit
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
        Row(horizontalArrangement = Arrangement.End){
            IconButton(onClick = { onCloseClick()}) {
                Icon(painter = painterResource(id = R.drawable.close),
                    contentDescription = null)
            }
        }
        Column(
            modifier = Modifier
                .padding(top = 4.dp, end = 16.dp,
                    start = 16.dp, bottom = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = product.description,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = quantity,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),

                    onValueChange = {
                        quantity = it
                        onQuantityChang(quantity.toIntOrNull()?: 0) },
                    label = { Text(stringResource(id = R.string.quantity_)) },
                    modifier = Modifier.weight(1f)
                )
                TextField(
                    value = cost,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    isError = cost.isEmpty(),
                    supportingText = {
                        if (cost.isEmpty()) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = R.string.fild_req),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    onValueChange = { cost = it
                        onCostChang(cost.toDoubleOrNull()?: 0.0)},
                    label = { Text(stringResource(id = R.string.cost_)) },
                    modifier = Modifier.weight(1f)
                )
                TextField(
                    value = price,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    isError = price.isEmpty() ||(cost.toDoubleOrNull() ?: 0.0) >= (price.toDoubleOrNull() ?: 0.0) ,
                    supportingText = {
                        if (price.isEmpty()) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = R.string.fild_req),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        if ((cost.toDoubleOrNull() ?: 0.0) >= (price.toDoubleOrNull() ?: 0.0)) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = R.string.price_err),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    onValueChange = { price = it
                        onPriceChang(price.toDoubleOrNull()?: 0.0)
                                    },
                    label = { Text(stringResource(id = R.string.price)) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }



}