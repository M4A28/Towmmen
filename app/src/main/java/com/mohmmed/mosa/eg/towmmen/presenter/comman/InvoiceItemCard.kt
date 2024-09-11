package com.mohmmed.mosa.eg.towmmen.presenter.comman

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.ui.theme.CairoFont
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtItemCard(
    itemName: String,
    price: Double,
    initialQuantity: Int = 1,
    onQuantityChange: (Int) -> Unit,
    onCloseClick: () -> Unit
) {
    var quantity by remember { mutableStateOf(initialQuantity) }
    val elevation by animateFloatAsState(if (quantity > 1) 8f else 2f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .shadow(elevation.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.End){
                IconButton(onClick = { onCloseClick()}) {
                    Icon(painter = painterResource(id = R.drawable.close),
                        contentDescription = null)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = itemName,
                    fontFamily = CairoFont,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){

                Text(
                    text = stringResource(id = R.string.price_, formatCurrency(price)),
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = CairoFont,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = stringResource(id = R.string.total_cost ,formatCurrency(price * quantity)),
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = CairoFont,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                QuantityControl(
                    quantity = quantity,
                    onIncrement = {
                        quantity++
                        onQuantityChange(quantity)
                    },
                    onDecrement = {
                        if (quantity > 1) {
                            quantity--
                            onQuantityChange(quantity)
                        }
                    }
                )
                Text(
                    text = stringResource(id = R.string.quantity, quantity),
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = CairoFont,

                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceItemCard(
    itemName: String,
    price: Double,
    initialQuantity: Int = 1,
    onQuantityChange: (Int) -> Unit,
    onCloseClick: () -> Unit
) {
    var quantity by remember { mutableStateOf(initialQuantity) }
    val elevation by animateFloatAsState(if (quantity > 1) 8f else 2f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .shadow(elevation.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.End){
                IconButton(onClick = { onCloseClick()}) {
                    Icon(painter = painterResource(id = R.drawable.close),
                        contentDescription = null)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = itemName,
                    fontFamily = CairoFont,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){

                Text(
                    text = stringResource(id = R.string.price_, formatCurrency(price)),
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = CairoFont,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = stringResource(id = R.string.total_cost ,formatCurrency(price * quantity)),
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = CairoFont,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                QuantityControl(
                    quantity = quantity,
                    onIncrement = {
                        quantity++
                        onQuantityChange(quantity)
                    },
                    onDecrement = {
                        if (quantity > 1) {
                            quantity--
                            onQuantityChange(quantity)
                        }
                    }
                )
                Text(
                    text = stringResource(id = R.string.quantity, quantity),
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = CairoFont,

                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun QuantityControl(
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .height(40.dp) // Set a fixed height for consistency
            .widthIn(min = 100.dp) // Set a minimum width
    ) {
        QuantityButton(
            onClick = onDecrement,
            enabled = quantity > 1,
            icon = painterResource(id = R.drawable.remove)
        )
        Text(
            text = "$quantity",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        QuantityButton(
            onClick = onIncrement,
            enabled = true,
            icon = painterResource(id = R.drawable.add)
        )
    }
}

@Composable
fun QuantityButton(
    onClick: () -> Unit,
    enabled: Boolean,
    icon: Painter
) {
    val backgroundColor by animateColorAsState(
        if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    )
    val contentColor by animateColorAsState(
        if (enabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
    )
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .padding(4.dp)
            .size(25.dp) // Increased size for better touch target
            .clip(CircleShape)
            .background(backgroundColor)
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(20.dp) // Adjust icon size as needed
        )
    }
}

