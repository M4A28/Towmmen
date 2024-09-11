package com.mohmmed.mosa.eg.towmmen.presenter.purchase

import android.Manifest
import android.media.MediaPlayer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Dealer
import com.mohmmed.mosa.eg.towmmen.data.module.Locker
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.data.module.Purchase
import com.mohmmed.mosa.eg.towmmen.data.module.PurchaseItem
import com.mohmmed.mosa.eg.towmmen.data.module.TransactionType
import com.mohmmed.mosa.eg.towmmen.presenter.comman.BarcodeReader
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ModernSearchBarWithBarcode
import com.mohmmed.mosa.eg.towmmen.presenter.purchase.commn.PurchaseDialog
import com.mohmmed.mosa.eg.towmmen.presenter.purchase.commn.PurchaseItemCard
import com.mohmmed.mosa.eg.towmmen.util.DEALER_KEY
import com.mohmmed.mosa.eg.towmmen.util.generatePurchaseNumber
import kotlinx.coroutines.delay
import java.util.Date


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddPurchaseScreen(navController: NavHostController) {
    val purchaseViewModel: PurchaseViewModel = hiltViewModel()
    val products by purchaseViewModel.products.collectAsState(initial = emptyList())
    val canSavePurchaseToDb  by purchaseViewModel.canSavePurchaseToDb.collectAsState()
    var cameraPermissionGranted by remember { mutableStateOf(false) }
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val context = LocalContext.current
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts
            .RequestPermission()
    ) { cameraPermissionGranted = it }

    LaunchedEffect(cameraPermissionState) {
        if (!cameraPermissionState.status.isGranted
            && cameraPermissionState.status.shouldShowRationale
        ) {
            Toast.makeText(context, context.getString(R.string.permission_is_important), Toast.LENGTH_LONG).show()

        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
    navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Dealer?>(DEALER_KEY)?.let {dealer ->
            AddPurchaseContent(
                products = products,
                onBackClick = {},
                dealer = dealer,
                onSaveInvoiceClick = { purchase , purchaseItems, selectedProducts ->
                    purchaseViewModel.insertFullPurchases(purchase , purchaseItems)
                    selectedProducts.forEach {product ->
                        val last = products.first { it.productId == product.productId }
                        last.stockQuantity += product.stockQuantity
                        last.cost = product.cost
                        last.price = product.price
                        purchaseViewModel.updateProduct(last)
                    }
                    if(canSavePurchaseToDb){
                        val locker = Locker(
                            transActionType = TransactionType.BUY.name,
                            transActionDate = Date(),
                            transActionAmount = purchase.totalCost * -1,
                            transActionNote = purchase.purchaseId
                        )
                        purchaseViewModel.upsertLockerTransaction(locker)
                    }
                    Toast.makeText(context,
                        context.getString(R.string.purchase_has_been_add), Toast.LENGTH_LONG).show()

                }
            )

        }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPurchaseContent(
    modifier: Modifier = Modifier,
    products: List<Product>,
    dealer: Dealer,
    onBackClick: () -> Unit,
    onSaveInvoiceClick: (Purchase, List<PurchaseItem>, List<Product>) -> Unit,
) {

    val context = LocalContext.current
    var showBarcodeScan by remember { mutableStateOf(false) }
    val purchaseProduct by remember { mutableStateOf(mutableListOf<Product>())}
    var barcodeValue by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf(barcodeValue.ifEmpty { "" }) }
    var purchasesId by remember { mutableStateOf(generatePurchaseNumber()) }
    var showPurchaseDialog by remember { mutableStateOf(false) }
    val scannerSound = remember { MediaPlayer.create(context, R.raw.scanner_beep) }
    val cashierSound = remember { MediaPlayer.create(context, R.raw.cashier_chingquot) }
    val purchaseDate by remember { mutableStateOf(Date()) }

    Scaffold(
        topBar = {
            TopAppBar(

                navigationIcon = {
                    IconButton(onClick = {
                        onBackClick()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ArrowBack"
                        )
                    }
                },
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(id = R.string.add_purchase))
                    }

                })
        }
    ) { padding ->
        val topPadding = padding.calculateTopPadding()
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(top = topPadding, end = 4.dp, start = 4.dp),
        ) {

            item{
                if(showBarcodeScan){
                    // Clean up MediaPlayer when leaving the composition
                    DisposableEffect(Unit) {
                        onDispose {
                            scannerSound.release()
                            cashierSound.release()
                        }
                    }
                    LaunchedEffect(barcodeValue) {
                        if(barcodeValue.isNotEmpty()){
                            try{
                                scannerSound.start()
                                delay(500L)
                                barcodeValue = ""
                            }catch (e: Exception){
                                e.printStackTrace()
                            }
                        }
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)){
                        BarcodeReader(onBarcodeDetected = {
                            barcodeValue = it
                        })
                    }
                }
                ModernSearchBarWithBarcode(
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    suggestions = products.map{it.name}.filter{ it.contains(searchQuery, ignoreCase = true) },
                    onSuggestionSelected = {searchQuery = it },
                    onBarcodeButtonClick = { showBarcodeScan = !showBarcodeScan }
                    )
            }
            val selectedProduct = products.firstOrNull() {
                it.barcode == searchQuery ||
                        it.barcode == barcodeValue ||
                        it.name == searchQuery
            }
            if (selectedProduct != null) {
                if(purchaseProduct.none { it.productId == selectedProduct.productId }){
                    purchaseProduct.add(selectedProduct.copy(stockQuantity = 0, updatedAt = purchaseDate ))
                }
            }


            if(purchaseProduct.isNotEmpty()){
                items(purchaseProduct){ item ->
                    PurchaseItemCard(product = item,
                        onCostChang = {item.cost = it},
                        onPriceChang = {item.price = it},
                        onQuantityChang = {item.stockQuantity = it},
                        onCloseClick = {
                            searchQuery =""
                            if(purchaseProduct.isNotEmpty()){
                                purchaseProduct.remove(item)
                            }
                        }
                    )
                }
                item{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Button(
                            onClick = {
                                showPurchaseDialog = true
                            }
                        ) { Text(stringResource(R.string.save_purchase)) }

                        Button(
                            onClick = {
                                purchaseProduct.clear()
                                searchQuery =""
                                purchasesId = generatePurchaseNumber()
                            }
                        ) { Text(stringResource(R.string.clear)) }
                    }
                }
            }

        }
    }
    if(showPurchaseDialog){
        val purchase = Purchase(
            purchaseId = purchasesId,
            dealerId = dealer.dealerId,
            dealerName = dealer.dealerName,
            totalCost = purchaseProduct.sumOf { it.cost * it.stockQuantity },
            date = Date()
        )
        val purchaseItems = mutableListOf<PurchaseItem>()
        purchaseProduct.forEach{
            if(it.stockQuantity > 0){
                purchaseItems.add(
                    PurchaseItem(
                        itemId = 0,
                        purchaseId = purchasesId,
                        productId = it.productId,
                        productName = it.name,
                        quantity = it.stockQuantity,
                        unitPrice = it.cost,
                        purchaseDate = purchaseDate
                    )
                )
            }
        }
        PurchaseDialog(purchase = purchase,
            purchaseItems = purchaseItems,
            onDismiss = {showPurchaseDialog = false},
            onConform = {
                try{
                    onSaveInvoiceClick(purchase, purchaseItems, purchaseProduct )
                    purchasesId = generatePurchaseNumber()
                    cashierSound.start()
                    purchaseProduct.clear()
                    searchQuery =""
                    showPurchaseDialog = false
                }catch (e: Exception){
                    e.printStackTrace()
                }

            })
    }
}
