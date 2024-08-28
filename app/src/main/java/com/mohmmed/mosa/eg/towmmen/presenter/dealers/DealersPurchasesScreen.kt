package com.mohmmed.mosa.eg.towmmen.presenter.dealers

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Purchase
import com.mohmmed.mosa.eg.towmmen.data.module.PurchaseWithItems
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ConfirmationDialog
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.purchase.PurchaseViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.purchase.commn.PurchaseCard
import com.mohmmed.mosa.eg.towmmen.util.DEALER_ID


@Composable
fun DealerPurchaseScreen(navController: NavHostController){
    val purchaseViewModel: PurchaseViewModel = hiltViewModel()
    var showDeleteDialog by remember { mutableStateOf(false) }
    val allProducts by purchaseViewModel.products.collectAsState(initial = emptyList())
    var purchase by remember{ mutableStateOf<Purchase?>(null) }
    var mPurchaseWithItems by remember{ mutableStateOf<PurchaseWithItems?>(null) }

    navController
        .previousBackStackEntry
        ?.savedStateHandle
        ?.get<Int?>(DEALER_ID)?.let {
            val purchaseWithItems by purchaseViewModel
                .getAllPurchasesWithItemsByDealerId(it)
                .collectAsState(initial = emptyList())

            DealerPurchaseContent(
                purchaseWithItems = purchaseWithItems,
                onEdit = {
                    // todo
                },
                onDelete = {
                        items ->
                    mPurchaseWithItems = items
                    purchase = items.purchase
                    showDeleteDialog = !showDeleteDialog
                }
            )
        }
    if(showDeleteDialog) {
        ConfirmationDialog(
            title = stringResource(R.string.delet_purchase),
            text = stringResource(R.string.purchase_delete_warring),
            dismissText = stringResource(id = R.string.cancel),
            confirmText = stringResource(id = R.string.delete),
            onConfirm = {
                mPurchaseWithItems?.let{
                    it.items.forEach { item ->
                        val product = allProducts.firstOrNull{ it.productId == item.productId }
                        if (product != null) {
                            if(product.stockQuantity -  item.quantity > 0){
                                product.stockQuantity -= item.quantity
                                purchaseViewModel.updateProduct(product)
                            }
                        }

                    }
                    purchaseViewModel.deletePurchase(it.purchase)

                }
                showDeleteDialog = !showDeleteDialog
            },
            onDismiss = { showDeleteDialog = !showDeleteDialog }
        )
    }


}

@Composable
fun DealerPurchaseContent(
    modifier: Modifier = Modifier,
    purchaseWithItems: List<PurchaseWithItems>,
    onEdit: (PurchaseWithItems) -> Unit,
    onDelete: (PurchaseWithItems) -> Unit
){
    if(purchaseWithItems.isNotEmpty()){
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {

            items(purchaseWithItems,
                key = { it.purchase.purchaseId }
            ){ purchaseWithItems ->
                PurchaseCard(
                    purchase = purchaseWithItems.purchase,
                    purchaseItems = purchaseWithItems.items,
                    onDeleteClick ={ onDelete(purchaseWithItems)},
                    onEditClick = {onEdit(purchaseWithItems) }
                )
            }

        }
    }else{
        EmptyScreen(massage = stringResource(R.string.no_purchase_for_this_dealer),
            icon = R.drawable.bag ,
            alphaAnim = 0.3f)
    }
}

