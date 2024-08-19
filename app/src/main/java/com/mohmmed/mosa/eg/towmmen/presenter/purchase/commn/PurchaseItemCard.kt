package com.mohmmed.mosa.eg.towmmen.presenter.purchase.commn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.ui.theme.CairoFont

@Composable
fun PurchaseItemCard(
    modifier: Modifier = Modifier,
    product: Product
) {

    var newPrice by remember { mutableStateOf("") }
    var newQuantity by  remember { mutableStateOf("") }
    var newCost by  remember { mutableStateOf("") }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = product.name,
                fontFamily = CairoFont,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = newCost,
                    onValueChange = { newCost = it },
                    label = { Text(stringResource(id = R.string.cost_)) },
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.cash),
                            contentDescription = null ,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                )

                TextField(
                    value = newPrice,
                    onValueChange = { newPrice = it },
                    label = { Text(stringResource(id = R.string.price)) },
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.money),
                            contentDescription = null ,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                )

                TextField(
                    value = newQuantity,
                    onValueChange = { newQuantity = it },
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.quantity),
                            contentDescription = null ,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    },
                    label = { Text(stringResource(id = R.string.quantity_)) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                )


            }
        }
    }



}