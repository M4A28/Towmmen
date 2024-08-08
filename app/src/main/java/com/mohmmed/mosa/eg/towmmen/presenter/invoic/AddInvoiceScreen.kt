package com.mohmmed.mosa.eg.towmmen.presenter.invoic

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.barcode.BarcodeAnalyzer
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.comman.InvoiceItemCard
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ModernSearchBar
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import com.mohmmed.mosa.eg.towmmen.util.CUSTOMER_KEY
import com.mohmmed.mosa.eg.towmmen.util.generateInvoiceNumber
import kotlinx.coroutines.delay
import java.util.Date


@Composable
fun AddInvoiceScreen(navController: NavHostController){
    val productsViewModel: ProductViewModel = hiltViewModel()
    val invoiceViewModel: InvoiceViewModel = hiltViewModel()
    val products by productsViewModel.products.collectAsState(initial = emptyList())

    navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Customer?>(CUSTOMER_KEY)?.let { customer ->
            AddInvoiceContent(products = products,
                customer = customer,
                onSaveInvoiceClick = {
                invoice, item ->
                invoiceViewModel.insertFullInvoice(invoice, item)
                item.forEach{invoiceItem ->
                    val edit = products.first { invoiceItem.productId == it.productId }
                    edit.stockQuantity -= invoiceItem.quantity
                    if(edit.stockQuantity >= 0) {
                        productsViewModel.updateProduct(edit)
                    }
                }
            })

        }
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
    var invoiceItem by remember { mutableStateOf(mutableSetOf<InvoiceItem>()) }
    var finalInvoice by remember{ mutableStateOf<Invoice?>(null) }
    var showBarcodeScan by remember { mutableStateOf(false) }
    var showInvoiceDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var invoiceId = generateInvoiceNumber()
    var invoiceDate = Date()
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.scanner_beep) }

    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
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
                                mediaPlayer.release()
                            }
                        }

                        LaunchedEffect(barcodeValue) {
                            if(barcodeValue.isNotEmpty()){
                                mediaPlayer.start()
                                delay(500L)
                                barcodeValue = ""
                            }
                        }
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)){
                            BarcodeReader(onBarcodeDetected = {
                                //searchQuery = it
                                barcodeValue = it
                            })
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            onClick = { showBarcodeScan = !showBarcodeScan },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.barcode),
                                contentDescription = null
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        ModernSearchBar(
                            searchQuery = searchQuery,
                            onSearchQueryChange = { searchQuery = it }
                        )
                    }
                }

                val p = products.filter { it.barcode == searchQuery ||
                        it.barcode == barcodeValue ||
                        it.name == searchQuery }
                p.forEach{
                    invoiceItem.add(
                        InvoiceItem(
                            //itemId = 0,
                            invoiceId = invoiceId,
                            productId = it.productId,
                            productName = it.name,
                            quantity = 1,
                            purchaseDate = invoiceDate,
                            unitPrice = it.price
                        )
                    )
                }

                items(invoiceItem.toList()){ item ->
                    if(invoiceItem.isNotEmpty()){
                        InvoiceItemCard(
                            itemName = item.productName ,
                            price = item.unitPrice,
                            initialQuantity = 1,
                            onQuantityChange = {
                                item.quantity = it
                            },
                            onCloseClick = {
                                invoiceItem = invoiceItem
                                    .toMutableSet().apply { remove(item) }
                            }
                        )
                    }else{
                        EmptyScreen(
                            massage = stringResource(R.string.no_item_added),
                            icon = R.drawable.box,
                            alphaAnim = 0.3f
                        )
                    }

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
                                    invoiceItem = mutableSetOf()
                                    invoiceId = generateInvoiceNumber()
                                }
                            ) { Text(stringResource(R.string.clear)) }
                        }
                    }
                }
        }

        if(showInvoiceDialog){
            InvoiceDialog(finalInvoice!!, invoiceItem.toList(),
                onDismiss = {
                showInvoiceDialog = !showInvoiceDialog

            },
                onConform = {
                    showInvoiceDialog = !showInvoiceDialog
                    onSaveInvoiceClick(finalInvoice!!, invoiceItem.toList())
                    invoiceItem = mutableSetOf()
                })
        }
    }

}

@Composable
fun BarcodeReader(onBarcodeDetected: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var cameraPermissionGranted by remember { mutableStateOf(false) }




    LaunchedEffect(Unit) {
        cameraPermissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    if (cameraPermissionGranted) {
        val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx).apply {
                    this.scaleType = PreviewView.ScaleType.FILL_CENTER
                }
                val executor = ContextCompat.getMainExecutor(ctx)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    val imageAnalyzer = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(executor, BarcodeAnalyzer(onBarcodeDetected))
                        }
                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_BACK_CAMERA,
                            preview,
                            imageAnalyzer
                        )
                    } catch (exc: Exception) {
                        Log.e("BarcodeScanner", "Use case binding failed", exc)
                    }
                }, executor)
                previewView
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                stringResource(R.string.camera_permission),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        }

        LaunchedEffect(Unit) {
            cameraPermissionGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}
