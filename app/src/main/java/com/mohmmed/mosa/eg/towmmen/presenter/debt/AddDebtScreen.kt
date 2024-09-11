package com.mohmmed.mosa.eg.towmmen.presenter.debt

import android.Manifest
import android.media.MediaPlayer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
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
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.data.module.Debt
import com.mohmmed.mosa.eg.towmmen.data.module.DebtItem
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.comman.BarcodeReader
import com.mohmmed.mosa.eg.towmmen.presenter.comman.InvoiceItemCard
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ModernSearchBarWithBarcode
import com.mohmmed.mosa.eg.towmmen.presenter.invoic.comman.DebtDialog
import com.mohmmed.mosa.eg.towmmen.util.CUSTOMER_KEY
import com.mohmmed.mosa.eg.towmmen.util.generateDebtNumber
import com.mohmmed.mosa.eg.towmmen.util.generateInvoiceNumber
import kotlinx.coroutines.delay
import java.util.Date

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddDebtScreen(navController: NavHostController){
    val debtViewModel: DebtViewModel = hiltViewModel()
    val products by debtViewModel.products.collectAsState()
    var cameraPermissionGranted by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
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
        ?.get<Customer?>(CUSTOMER_KEY)?.let { customer ->
            AddDebtContent(products = products,
                customer = customer,
                onSaveInvoiceClick = {
                        debt, item ->
                    debtViewModel.insertFullDebt(debt, item)
                    item.forEach{invoiceItem ->
                        val edit = products.first { invoiceItem.productId == it.productId }
                        edit.stockQuantity -= invoiceItem.quantity
                        if(edit.stockQuantity >= 0) {
                            debtViewModel.updateProduct(edit)
                        }
                    }
                    Toast.makeText(context,
                        context.getString(R.string.debt_hase_been_add), Toast.LENGTH_LONG).show()

                })
        }




}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDebtContent(
    products: List<Product>,
    customer: Customer,
    onSaveInvoiceClick: (Debt, List<DebtItem>) -> Unit
) {
    var barcodeValue by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf(barcodeValue.ifEmpty { "" }) }
    val debtItem by remember { mutableStateOf(mutableListOf<DebtItem>()) }
    var finalDebt by remember{ mutableStateOf<Debt?>(null) }
    var showBarcodeScan by remember { mutableStateOf(false) }
    var showDebtDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var debtId = generateDebtNumber()

    var debtDate = Date()
    val scannerSound = remember { MediaPlayer.create(context, R.raw.scanner_beep) }
    val cashierSound = remember { MediaPlayer.create(context, R.raw.cashier_chingquot) }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) { Text(text = stringResource(id = R.string.debt_)) }
                }
            )
        }
    ){ paddingValue ->
        val topPadding = paddingValue.calculateTopPadding()
        LazyColumn(modifier = Modifier.padding(top = topPadding, start = 4.dp, end = 4.dp)) {
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
                    suggestions = products.map { it.name }.filter{ it.contains(searchQuery, ignoreCase = true) },
                    onSuggestionSelected = {searchQuery = it },
                    onBarcodeButtonClick = { showBarcodeScan = !showBarcodeScan },
                )
            }

            val selectedProduct = products.firstOrNull {
                it.barcode == searchQuery ||
                        it.barcode == barcodeValue ||
                        it.name == searchQuery
            }

            if (selectedProduct != null) {
                if(debtItem.none { it.productId == selectedProduct.productId }){
                    debtItem.add(
                        DebtItem(
                            debtItemId = 0,
                            productId = selectedProduct.productId,
                            productName = selectedProduct.name,
                            quantity = 1,
                            date = debtDate,
                            unitPrice = selectedProduct.price,
                            debtId = debtId,
                        ))
                }
            }
            items(debtItem){ item ->
                InvoiceItemCard(
                    itemName = item.productName ,
                    price = item.unitPrice,
                    initialQuantity = 1,
                    onQuantityChange = {
                        item.quantity = it
                    },
                    onCloseClick = {
                        if(debtItem.isNotEmpty()){
                            debtItem.remove(item)
                            searchQuery = ""
                        }
                    }
                )
            }

            if(debtItem.isNotEmpty()){
                item{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Button(
                            onClick = {
                                val debt = Debt(
                                    debtId = debtId,
                                    customerId = customer.customerId,
                                    customerName = customer.name,
                                    debtDate = debtDate,
                                    debtAmount = debtItem.sumOf {
                                        it.unitPrice * it.quantity
                                    },
                                    isPayed = false,
                                    payDate = null
                                )
                                finalDebt = debt
                                showDebtDialog = !showDebtDialog
                                debtId = generateInvoiceNumber()

                            }
                        ) { Text(stringResource(R.string.save_debt)) }

                        OutlinedButton(
                            onClick = {
                                debtItem.clear()
                                searchQuery = ""
                                debtId = generateInvoiceNumber()
                            }
                        ) { Text(stringResource(R.string.clear)) }
                    }
                }
            }
        }

        if(showDebtDialog){
            DebtDialog(finalDebt!!, debtItem,
                onDismiss = {
                    showDebtDialog = !showDebtDialog
                },
                onConform = {
                    try{
                        onSaveInvoiceClick(finalDebt!!, debtItem.toMutableList())
                        showDebtDialog = false
                        cashierSound.start()
                        debtItem.clear()
                        debtDate = Date()
                        searchQuery = ""
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                })
        }
    }

}

