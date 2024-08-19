package com.mohmmed.mosa.eg.towmmen.presenter.test

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.barcode.BarcodeAnalyzer
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.barcode.BarcodeScannerViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.comman.InvoiceItemCard
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ModernSearchBar
import com.mohmmed.mosa.eg.towmmen.presenter.invoic.BarcodeReader
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import kotlinx.coroutines.delay
import java.util.Date
import java.util.UUID


@Composable
fun AddInvoiceScreen2(){
    val productsViewModel: ProductViewModel = hiltViewModel()
    val products by productsViewModel.products.collectAsState(initial = emptyList())
    AddInvoiceContent2(products = products,
        onFapClick = {},
        onScanClick = {})
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInvoiceContent2(
    products: List<Product>,
    onFapClick: () -> Unit,
    onScanClick: () -> Unit
) {
    var barcodeValue by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf(barcodeValue.ifEmpty { "" }) }
    var invoiceItem by remember { mutableStateOf(mutableSetOf<InvoiceItem>()) }
    var showBarcodeScan by remember { mutableStateOf(false) }
    var productName by remember { mutableStateOf("") }
    val invoiceVm: TestViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val radomCustomer by invoiceVm.getAllCustomres().collectAsState(initial = emptyList())

    var invoceId = UUID.randomUUID()

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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                },
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(4.dp)
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }

    ){ paddingValue ->
        val topPadding = paddingValue.calculateTopPadding()
        LazyColumn(modifier = Modifier.padding(top = topPadding, start = 4.dp, end = 4.dp)) {

            item{

                if(showBarcodeScan){
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)){
                        BarcodeReader(onBarcodeDetected = {
                            searchQuery = it
                        })
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = { showBarcodeScan = !showBarcodeScan },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(stringResource(R.string.scan))
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    ModernSearchBar(
                        searchQuery = searchQuery,
                        onSearchQueryChange = { searchQuery = it }
                    )
                }
            }

            val p = products.filter { it.barcode == searchQuery || it.name == searchQuery }
            p.forEach{
                invoiceItem.add(
                    InvoiceItem(
                        //itemId = 0,
                        invoiceId = invoceId.toString(),
                        productId = it.productId,
                        productName = it.name,
                        quantity = 1,
                        unitPrice = it.price,
                        purchaseDate = Date()
                    )
                )
            }

            items(invoiceItem.toList()){ item ->
                InvoiceItemCard(
                    itemName = item.productName ,
                    price = item.unitPrice,
                    initialQuantity = 1,
                    onQuantityChange = {
                        item.quantity = it
                    },
                    onCloseClick = {
                        invoiceItem = invoiceItem.toMutableSet().apply { remove(item) }
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
                                    invoiceId = invoceId.toString(),
                                    customerId = radomCustomer.get(1).customerId,
                                    customerName = radomCustomer.get(1).name,
                                    date = Date(),
                                    totalAmount = invoiceItem.sumOf {
                                        it.unitPrice * it.quantity
                                    }
                                )
                                invoiceVm.insertFullInvoice(invoice, invoiceItem.toList())
                                invoiceItem.forEach{ item ->
                                    val edit = products.filter { item.productId == it.productId }.first()
                                    edit.stockQuantity -= item.quantity
                                    if(edit.stockQuantity >= 0) {
                                        invoiceVm.updateProducat(edit)
                                        Log.d("code", "${edit.stockQuantity}")
                                    }
                                }
                                Log.d("code", invoice.invoiceId)
                                Log.d("code", "${invoice.totalAmount}")
                                Log.d("code", "${invoiceItem.size}")
                                Log.d("code", "${invoiceItem.first().itemId}")
                            }
                        ) { Text("Save Invoice") }

                        Button(
                            onClick = {  }
                        ) { Text("Delete") }
                    }
                }
            }

        }

    }

}

@Composable
fun BarcodeReader2(onBarcodeDetected: (String) -> Unit) {
    val context = LocalContext.current
    val viewModel: BarcodeScannerViewModel = hiltViewModel()
    val barcode by viewModel.barcode.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    var cameraPermissionGranted by remember { mutableStateOf(false) }

    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.scanner_beep)
    }

    // Clean up MediaPlayer when leaving the composition
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    LaunchedEffect(barcode) {
        if (barcode != null) {

            mediaPlayer.start()
            delay(500L)
            Log.d("code", barcode!!)
        }
    }
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
