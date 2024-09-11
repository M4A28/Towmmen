package com.mohmmed.mosa.eg.towmmen.presenter.debt

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastForEach
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.data.module.DebtWithItem
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ConfirmationDialog
import com.mohmmed.mosa.eg.towmmen.presenter.comman.CustomTriDropDownMenu
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.util.CUSTOMER_KEY
import com.mohmmed.mosa.eg.towmmen.util.generateInvoiceNumber
import java.util.Date


@Composable
fun CustomerDebtScreen(navController: NavHostController){
    navController
        .previousBackStackEntry
        ?.savedStateHandle
        ?.get<Customer?>(CUSTOMER_KEY)?.let { customer ->
            val debtViewModel: DebtViewModel = hiltViewModel()
            var showDeleteDialog by remember { mutableStateOf(false) }
            val customerDebt by debtViewModel.getDebtByCustomerId(customer.customerId).collectAsState(listOf())
            //val customerDebt = alldebt.filter { it.debt.customerId == customer.customerId }
            var debtWithItems by remember { mutableStateOf<DebtWithItem?>(null) }
            val context = LocalContext.current

            CustomerDebtContent(
                debtWitItems = customerDebt,
                onDeleteDebt = {debtWithItems = it
                    showDeleteDialog = true},
                onEditDebt = {debtWithItems = it}, // todo it
                onPaidClick = {
                    if(!it.debt.isPayed){
                        debtViewModel.upsertDebt(it.debt.copy(isPayed = true, payDate = Date()))
                        val invoice = Invoice(
                            invoiceId = generateInvoiceNumber(),
                            customerId = it.debt.customerId,
                            customerName = it.debt.customerName,
                            date = Date(),
                            profit = 0.0, // it debt
                            totalAmount = it.debt.debtAmount
                        )
                        val invoiceItems = mutableListOf<InvoiceItem>()
                        it.items.fastForEach { debtItem ->
                            val invoiceItem = InvoiceItem(
                                invoiceId = invoice.invoiceId,
                                productId = debtItem.productId,
                                productName = debtItem.productName,
                                quantity = debtItem.quantity,
                                unitPrice = debtItem.unitPrice,
                                unitCost = 0.0,
                                purchaseDate = Date()
                            )
                            invoiceItems.add(invoiceItem)
                        }

                        debtViewModel.insertFullInvoice(invoice, invoiceItems)
                        Toast.makeText(context, context.getString(R.string.paid_done), Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(context, context.getString(R.string.paid_done_before), Toast.LENGTH_SHORT).show()
                    }

                }
            )
            if(showDeleteDialog){
                ConfirmationDialog(
                    title = stringResource(R.string.delete_debt),
                    text = stringResource(R.string.conform_delete_debt),
                    confirmText = stringResource(id = R.string.delete),
                    dismissText = stringResource(id = R.string.dismiss),
                    onConfirm = {
                        if(debtWithItems != null){
                            debtViewModel.deleteDebt(debtWithItems!!.debt)
                            debtViewModel.deleteDebtItem(debtWithItems!!.items)
                            showDeleteDialog = false
                            Toast.makeText(context, context.getString(R.string.item_deleted), Toast.LENGTH_SHORT).show()
                        }
                    },
                    onDismiss = {
                        showDeleteDialog = false
                    })


            }

        }







}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDebtContent(
    debtWitItems: List<DebtWithItem>,
    onPaidClick: (DebtWithItem) -> Unit,
    onDeleteDebt: (DebtWithItem) -> Unit,
    onEditDebt:  (DebtWithItem) -> Unit
){
    var selectedFilter by remember { mutableStateOf("0") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(id = R.string.debt_))

                    }

                },
                actions = {
                    CustomTriDropDownMenu(
                        actionOne = stringResource(R.string.show_all_debt),
                        actionTwo = stringResource(R.string.show_not_paid_debt),
                        actionThree = stringResource(R.string.show_paid_debt),
                        onActionOne = {selectedFilter = "0"},
                        onActionTwo = {selectedFilter = "1"},
                        onActionThree = {selectedFilter = "2"}
                    )
                })
        }
    ) { paddingValue ->

        val filteredDebt = when(selectedFilter){
            "0" -> debtWitItems
            "1" -> debtWitItems.filter { !it.debt.isPayed}
            "2" -> debtWitItems.filter { it.debt.isPayed}
            else -> {debtWitItems}
        }
        if(filteredDebt.isNotEmpty()){
            LazyColumn(modifier = Modifier.padding(top = paddingValue.calculateTopPadding())) {
                items(filteredDebt, key = {
                    it.debt.debtId
                }){ debtWithItems ->

                    DebtCard(debt = debtWithItems.debt,
                        debtItems = debtWithItems.items,
                        onPaidClick = { onPaidClick(debtWithItems)},
                        onDeleteClick = { onDeleteDebt(debtWithItems)},
                        onEditClick = { onEditDebt(debtWithItems) }
                    )

                }


            }
        }else{
            EmptyScreen(massage = stringResource(R.string.no_debt_found), icon = R.drawable.cash, alphaAnim = 0.4f)
        }
    }

}
