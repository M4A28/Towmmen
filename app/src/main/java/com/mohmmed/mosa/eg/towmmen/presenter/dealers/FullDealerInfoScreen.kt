package com.mohmmed.mosa.eg.towmmen.presenter.dealers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Dealer
import com.mohmmed.mosa.eg.towmmen.presenter.comman.DeleteConfirmationDialog
import com.mohmmed.mosa.eg.towmmen.presenter.comman.DetailItem
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.Route
import com.mohmmed.mosa.eg.towmmen.util.DEALER_KEY
import com.mohmmed.mosa.eg.towmmen.util.dateToString
import kotlinx.coroutines.launch

@Composable
fun FullDealerInfoScreen(navController: NavHostController) {
    val dealerViewModel: DealerViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Dealer?>(DEALER_KEY)?.let {
            val dealer by dealerViewModel.getDealerById(it.dealerId).collectAsState(initial = it)
            FullDealerInfoContent(
                dealer = dealer,
                onDeleteClick = {
                    showDeleteConfirmation = true
                },
                onEditClick = {
                    showEditDialog = true
                },
                onPurchaseClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(DEALER_KEY, it)
                    navController.navigate(Route.DealerPurchasesScreen.route)
                },
                onShowInvoicesClick = {
                    // todo  make all invoice screen

                }
            )


            if (showDeleteConfirmation) {
                DeleteConfirmationDialog(
                    title = stringResource(id = R.string.confirm_deletion),
                    massage = stringResource(id = R.string.delete_dealer),
                    onConfirm = {
                        coroutineScope.launch {
                            dealerViewModel.deleteDealer(dealer)
                            navController.popBackStack()
                        }
                    },
                    onDismiss = { showDeleteConfirmation = false }
                )
            }


            if(showEditDialog){
                EditDealerDialog(dealer = dealer ,
                    onEditClick = { dealerViewModel.upsertDealer(it) },
                    showDialog = { showEditDialog = it })
            }
        }






}

@Composable
fun FullDealerInfoContent(
    modifier: Modifier = Modifier,
    dealer: Dealer,
    onDeleteClick:  (Dealer)  -> Unit,
    onEditClick: (Dealer)  -> Unit,
    onPurchaseClick: (Dealer) -> Unit = {},
    onShowInvoicesClick: (Dealer) -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        DealerHeader(dealer,
            onEditClick = {onEditClick(dealer)},
            onDeleteClick = { onDeleteClick(dealer) })
        Spacer(modifier = Modifier.height(16.dp))
        DealerDetails(dealer)
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            Button(
                onClick = { onPurchaseClick(dealer) },
                colors = ButtonDefaults
                    .buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.record_purchase),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Button(
                onClick = {onShowInvoicesClick(dealer)},
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Spacer(Modifier.width(8.dp))
                Text(text = stringResource(R.string.show_invoices),
                    style = MaterialTheme.typography.labelMedium)
            }
        }
    }

}



@Composable
fun DealerHeader(
    dealer: Dealer,
    onEditClick: (Dealer) -> Unit,
    onDeleteClick: (Dealer) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.person),
            contentDescription = "Dealer Image",
            alpha = 0.4f,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {

            FilledIconButton(
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                onClick = { onEditClick(dealer) }) {
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    painter = painterResource(id = R.drawable.edit ),
                    contentDescription = null
                )
            }

            Spacer(Modifier.width(32.dp))

            FilledIconButton(
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                onClick = { onDeleteClick(dealer) }) {
                Icon(
                    tint = MaterialTheme.colorScheme.error,
                    painter = painterResource(id = R.drawable.delete ),
                    contentDescription = null
                )
            }
        }

        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            fontWeight = FontWeight.Bold,
            text = dealer.dealerName,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun DealerDetails(dealer: Dealer) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            DetailItem(icon = R.drawable.phone, label = stringResource(id = R.string.phone), value = dealer.dealerPhoneNumber)
            DetailItem(icon = R.drawable.location_pin, label = stringResource(id = R.string.address), value = dealer.dealerAddress)
            DetailItem(icon = R.drawable.notes, label = stringResource(id = R.string.notes), value = dealer.dealerNote)
            DetailItem(
                icon = R.drawable.calendar_month,
                label = stringResource(id = R.string.reg_data_2),
                value = dateToString(dealer.createDate, "yyyy-MM-dd")
            )
        }
    }
}