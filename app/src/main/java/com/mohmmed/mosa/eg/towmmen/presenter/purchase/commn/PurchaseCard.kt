package com.mohmmed.mosa.eg.towmmen.presenter.purchase.commn

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Purchase
import com.mohmmed.mosa.eg.towmmen.data.module.PurchaseItem
import com.mohmmed.mosa.eg.towmmen.presenter.comman.SaveEditDropDownMenu
import com.mohmmed.mosa.eg.towmmen.util.dateToString
import java.util.Locale

@Composable
fun PurchaseCard(purchase: Purchase,
                 purchaseItems: List<PurchaseItem>,
                 onEditClick: (Purchase) -> Unit = {},
                 onDeleteClick: (Purchase) -> Unit = {}
) {

    var showItems by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .clickable { showItems = !showItems },
        elevation = CardDefaults.elevatedCardElevation(8.dp)
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
                    text = stringResource(id = R.string.purchase),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(50.dp))
                Text(
                    text = purchase.purchaseId,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                SaveEditDropDownMenu(
                    onEdit = {onEditClick(purchase)},
                    onDelete = {onDeleteClick(purchase)})
            }
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(id = R.string.purchase_date,
                    dateToString(purchase.date,
                        pattern = "yyyy-MM-dd",
                        locale = Locale.getDefault())
                ) ,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(R.string.dealer_name_, purchase.dealerName),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(id = R.string.total, purchase.totalCost),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            if(showItems){

                purchaseItems.forEach{ item ->
                    ListItem(
                        headlineContent = { Text(item.productName) },
                        supportingContent = { Text(stringResource(id = R.string.quantity, item.quantity)) },
                        trailingContent = { Text(stringResource(id = R.string.price_, item.unitPrice )) }
                    )
                    HorizontalDivider()
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

/*
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {  }) {
                    Icon(
                        painter = painterResource(R.drawable.edit),
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                IconButton(onClick = {  }) {
                    Icon(
                        painter = painterResource(R.drawable.delete),
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }*/

        }
    }
}

