package com.mohmmed.mosa.eg.towmmen.presenter.debt

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
import com.mohmmed.mosa.eg.towmmen.data.module.Debt
import com.mohmmed.mosa.eg.towmmen.data.module.DebtItem
import com.mohmmed.mosa.eg.towmmen.presenter.comman.CustomTriDropDownMenu
import com.mohmmed.mosa.eg.towmmen.ui.theme.Green40
import com.mohmmed.mosa.eg.towmmen.util.ONE_DAY
import com.mohmmed.mosa.eg.towmmen.util.dateToString
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency
import java.util.Locale

@Composable
fun DebtCard(debt: Debt,
             debtItems: List<DebtItem>,
             onPaidClick: (Debt) -> Unit = {},
             onEditClick: (Debt) -> Unit = {},
             onDeleteClick: (Debt) -> Unit = {}
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
                    text = stringResource(id = R.string.debt_),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(50.dp))

                Text(
                    text = debt.debtId,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                CustomTriDropDownMenu(
                    actionOne = stringResource(R.string.paid_done),
                    actionTwo = stringResource(id = R.string.delete),
                    actionThree = stringResource(id = R.string.edit),
                    onActionOne = {onPaidClick(debt)},
                    onActionTwo = {onDeleteClick(debt) },
                    onActionThree = {onEditClick(debt)},
                )

            }
            Spacer(modifier = Modifier.height(6.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = stringResource(id = R.string.debt_date,
                        dateToString(debt.debtDate,
                            pattern = "yyyy-MM-dd",
                            locale = Locale.getDefault())
                    ) ,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = stringResource(id = R.string.pay_debt_date,
                        if(debt.payDate != null && debt.payDate.time > 0){
                            dateToString(debt.debtDate,
                                pattern = "yyyy-MM-dd",
                                locale = Locale.getDefault())
                        }else stringResource(R.string.not_paid_yet)

                    ) ,
                    fontWeight = FontWeight.Bold,
                    color = if(debt.isPayed) Green40 else  MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }


            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(id = R.string.customer_name, debt.customerName),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )

            if(debt.isPayed){
                Text(
                    text = stringResource(R.string.paid_done_in, (debt.payDate?.time?.minus(debt.debtDate.time))?.div(
                        ONE_DAY
                    ) ?: 0 ),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(id = R.string.total, formatCurrency(debt.debtAmount)),
                color = if(debt.isPayed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            if(showItems){

                debtItems.forEach{ item ->
                    ListItem(
                        headlineContent = { Text(item.productName) },
                        supportingContent = { Text(stringResource(id = R.string.quantity, item.quantity)) },
                        trailingContent = { Text(stringResource(id = R.string.price_, formatCurrency(item.unitPrice))) }
                    )
                    HorizontalDivider()
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}


