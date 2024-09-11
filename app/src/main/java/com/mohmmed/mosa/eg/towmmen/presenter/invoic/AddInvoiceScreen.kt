package com.mohmmed.mosa.eg.towmmen.presenter.invoic

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
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.data.module.Locker
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.data.module.TransactionType
import com.mohmmed.mosa.eg.towmmen.presenter.comman.BarcodeReader
import com.mohmmed.mosa.eg.towmmen.presenter.comman.InvoiceItemCard
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ModernSearchBarWithBarcode
import com.mohmmed.mosa.eg.towmmen.presenter.invoic.comman.InvoiceDialog2
import com.mohmmed.mosa.eg.towmmen.util.CUSTOMER_KEY
import com.mohmmed.mosa.eg.towmmen.util.generateInvoiceNumber
import kotlinx.coroutines.delay
import java.util.Date

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddInvoiceScreen(navController: NavHostController){
    // todo refactor this
    val invoiceViewModel: InvoiceViewModel = hiltViewModel()
    val canSaveSell  by invoiceViewModel.canSaveSellToDb.collectAsState()
    val products by invoiceViewModel.products.collectAsState(initial = emptyList())
    var cameraPermissionGranted by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts
            .RequestPermission()
    ) { cameraPermissionGranted = it }
    var customer by remember { mutableStateOf(
        Customer(
            customerId = -1,
            name = context.getString(R.string.without_customer),
            email = "",
            phone = "",
            address = "",
            registrationDate = Date(),
            lastPurchaseDate = Date()
        )
    ) }

    LaunchedEffect(cameraPermissionState) {
        if (!cameraPermissionState.status.isGranted
            && cameraPermissionState.status.shouldShowRationale
        ) {

            Toast.makeText(context,
                context.getString(R.string.permission_is_important),
                Toast.LENGTH_LONG).show()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Customer?>(CUSTOMER_KEY)?.let {
            customer = it
        }

    AddInvoiceContent(products = products,
        customer = customer,
        onSaveInvoiceClick = {
                invoice, item ->
            val purchaseDate = Date()
            invoiceViewModel.insertFullInvoice(invoice, item)
            invoiceViewModel.updateCustomer(customer.copy(lastPurchaseDate = purchaseDate))
            item.forEach{invoiceItem ->
                val edit = products.first { invoiceItem.productId == it.productId }
                edit.stockQuantity -= invoiceItem.quantity
                if(edit.stockQuantity >= 0) {
                    invoiceViewModel.updateProduct(edit)
                }
            }
            if(canSaveSell){
                invoiceViewModel.upsertLockerTransaction(Locker(
                    transActonId = 0,
                    transActionType = TransactionType.SELL.name,
                    transActionDate = purchaseDate,
                    transActionAmount = invoice.totalAmount,
                    transActionNote = invoice.invoiceId
                ))
            }
            Toast.makeText(context,
                context.getString(R.string.invoice_has_been_add), Toast.LENGTH_LONG).show()

        })



}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInvoiceContent(
    products: List<Product>,
    customer: Customer,
    onSaveInvoiceClick: (Invoice, List<InvoiceItem>) -> Unit
) {
    var barcodeValue by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf(barcodeValue.ifEmpty { "" }) }
    val invoiceItem by remember { mutableStateOf(mutableListOf<InvoiceItem>()) }
    var finalInvoice by remember{ mutableStateOf<Invoice?>(null) }
    var showBarcodeScan by remember { mutableStateOf(false) }
    var showInvoiceDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var invoiceId = generateInvoiceNumber()

    var invoiceDate = Date()
    val scannerSound = remember { MediaPlayer.create(context, R.raw.scanner_beep) }
    val cashierSound = remember { MediaPlayer.create(context, R.raw.cashier_chingquot) }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) { Text(text = stringResource(id = R.string.invoice)) }
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
                if(invoiceItem.none { it.productId == selectedProduct.productId }){
                    invoiceItem.add(InvoiceItem(
                        invoiceId = invoiceId,
                        productId = selectedProduct.productId,
                        productName = selectedProduct.name,
                        quantity = 1,
                        purchaseDate = invoiceDate,
                        unitPrice = selectedProduct.price,
                        unitCost = selectedProduct.cost
                    ))
                }
            }
                items(invoiceItem){ item ->
                    InvoiceItemCard(
                        itemName = item.productName ,
                        price = item.unitPrice,
                        initialQuantity = 1,
                        onQuantityChange = {
                            item.quantity = it
                        },
                        onCloseClick = {
                            if(invoiceItem.isNotEmpty()){
                                invoiceItem.remove(item)
                                searchQuery = ""
                            }
                        }
                    )
                }

                if(invoiceItem.isNotEmpty()){
                    item{
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Button(
                                onClick = {
                                    val invoice = Invoice(
                                        invoiceId = invoiceId,
                                        customerId = customer.customerId,
                                        customerName = customer.name,
                                        date = invoiceDate,
                                        totalAmount = invoiceItem.sumOf {
                                            it.unitPrice * it.quantity
                                        },
                                        profit = invoiceItem.sumOf {
                                            it.unitPrice * it.quantity
                                        } - invoiceItem.sumOf {
                                            it.unitCost * it.quantity
                                        }
                                    )
                                    finalInvoice = invoice
                                    showInvoiceDialog = !showInvoiceDialog
                                    invoiceId = generateInvoiceNumber()
                                    invoiceDate = Date()
                                }
                            ) { Text(stringResource(R.string.save_invoice)) }

                            Button(
                                onClick = {
                                    invoiceItem.clear()
                                    searchQuery = ""
                                    invoiceId = generateInvoiceNumber()
                                }
                            ) { Text(stringResource(R.string.clear)) }
                        }
                    }
                }
        }

        if(showInvoiceDialog){
            InvoiceDialog2(finalInvoice!!, invoiceItem,
                onDismiss = {
                showInvoiceDialog = !showInvoiceDialog
            },
                onConform = {
                    try{
                        onSaveInvoiceClick(finalInvoice!!.copy(customerName = it), invoiceItem.toMutableList())
                        showInvoiceDialog = false
                        cashierSound.start()
                        invoiceItem.clear()
                        searchQuery = ""

                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                })
        }
    }

}

