package com.mohmmed.mosa.eg.towmmen.presenter.locker

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Locker
import com.mohmmed.mosa.eg.towmmen.data.module.TransactionType
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.locker.comman.LockerInfoCard
import com.mohmmed.mosa.eg.towmmen.presenter.locker.comman.LockerItem
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.Route
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.navigateToScreen
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency

@Composable
fun LockerTransactionsScreen(navHostController: NavHostController){
    val lockerViewModel: LockerViewModel = hiltViewModel()
    val lockerTransactions by lockerViewModel.lockersTransaction.collectAsState()
    LockerTransactionsContent(
        lockersTransaction = lockerTransactions,
        onFapClick = {
            navigateToScreen(navHostController, Route.AddLockerScreen.route)
        },
        onDelete = {
            lockerViewModel.deleteLockerTransaction(it)
        },
        onEdit = {
            // todo
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LockerTransactionsContent(
    lockersTransaction: List<Locker>,
    onFapClick:() -> Unit,
    onEdit:(Locker) -> Unit,
    onDelete:(Locker) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(id = R.string.locker))
                    }

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onFapClick()
                }
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }

        }
    ) { innerPadding ->
        if(lockersTransaction.isNotEmpty()){

            LazyColumn(
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
            )
            {
                item{
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){

                        LockerInfoCard(
                            title = stringResource(id = R.string.total_),
                            value = formatCurrency(lockersTransaction.sumOf { it.transActionAmount }),
                            icon = { Icon(painter = painterResource(id = R.drawable.cash), contentDescription = "Total", tint = MaterialTheme.colorScheme.onSurfaceVariant) },
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                        LockerInfoCard(
                            title = stringResource(id = R.string.add),
                            value = formatCurrency(lockersTransaction
                                .filter { it.transActionType == TransactionType.ADD.name }
                                .sumOf { it.transActionAmount }),
                            icon = { Icon(painter = painterResource(id = R.drawable.plus),
                                contentDescription = "Add", tint = MaterialTheme.colorScheme.onSurfaceVariant) },
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                        LockerInfoCard(
                            title = stringResource(id = R.string.discount),
                            value = formatCurrency(lockersTransaction
                                .filter { it.transActionType == TransactionType.DISCOUNT.name }
                                .sumOf { it.transActionAmount }),
                            icon = { Icon(painter = painterResource(id = R.drawable.sup),
                                contentDescription = "Discount", tint = MaterialTheme.colorScheme.onSurfaceVariant) },
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                        LockerInfoCard(
                            title = stringResource(id = R.string.sell),
                            value = formatCurrency(lockersTransaction
                                .filter { it.transActionType == TransactionType.SELL.name }
                                .sumOf { it.transActionAmount }),
                            icon = { Icon(
                                painter = painterResource(id = R.drawable.bag),
                                contentDescription = "Sell", tint = MaterialTheme.colorScheme.onSurfaceVariant) },
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                        LockerInfoCard(
                            title = stringResource(id = R.string.buy),
                            value = formatCurrency(lockersTransaction
                                .filter { it.transActionType == TransactionType.BUY.name }
                                .sumOf { it.transActionAmount }),
                            icon = { Icon(
                                painter = painterResource(id = R.drawable.shopping_cart),
                                contentDescription = "Buy", tint = MaterialTheme.colorScheme.onSurfaceVariant) },
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )

                    }
                }
                items(lockersTransaction,
                    key = {it.transActonId}
                ) {
                        transaction ->
                    LockerItem(transaction,
                        onEdit = {onEdit(it)},
                        onDelete = {onDelete(it)}
                    )
                }
            }
        } else{
            EmptyScreen(
                massage = stringResource(R.string.no_transaction_yet) ,
                icon = R.drawable.locker,
                alphaAnim = 0.6f
            )
        }
    }
}