package com.mohmmed.mosa.eg.towmmen.presenter.purchase

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.PurchaseWithItems
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ConfirmationDialog
import com.mohmmed.mosa.eg.towmmen.presenter.dealers.DealerPurchaseContent

@Composable
fun PurchaseScreen(){

    val purchaseViewModel: PurchaseViewModel = hiltViewModel()
    var showDeleteDialog by remember { mutableStateOf(false) }
    val allProducts by purchaseViewModel.products.collectAsState(initial = emptyList())
    val purchaseWithItems by purchaseViewModel.getAllPurchasesWithItems().collectAsState(initial = emptyList())
    var mPurchaseWithItems by remember{ mutableStateOf<PurchaseWithItems?>(null) }
    val context = LocalContext.current


    DealerPurchaseContent(
        purchaseWithItems = purchaseWithItems,
        onEdit = {
            // todo
        },
        onDelete = {
                items ->
            mPurchaseWithItems = items
            showDeleteDialog = !showDeleteDialog
        }
    )

    if(showDeleteDialog) {
        ConfirmationDialog(
            title = stringResource(R.string.delet_purchase),
            text = stringResource(R.string.purchase_delete_warring),
            dismissText = stringResource(id = R.string.cancel),
            confirmText = stringResource(id = R.string.delete),
            onConfirm = {
                mPurchaseWithItems?.let{
                    it.items.forEach { item ->
                        val product = allProducts.firstOrNull { it.productId == item.productId }
                        if (product != null) {
                            if(product.stockQuantity -  item.quantity > 0){
                                product.stockQuantity -= item.quantity
                                purchaseViewModel.updateProduct(product)
                            }
                        }
                    }
                    purchaseViewModel.deletePurchase(it.purchase)
                    Toast.makeText(context,
                       context.getString(R.string.purchace_delete),
                        Toast.LENGTH_SHORT).show()
                }
                showDeleteDialog = !showDeleteDialog
            },
            onDismiss = { showDeleteDialog = !showDeleteDialog }
        )
    }
}