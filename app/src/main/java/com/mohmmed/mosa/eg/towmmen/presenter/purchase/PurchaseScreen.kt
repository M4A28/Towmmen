package com.mohmmed.mosa.eg.towmmen.presenter.purchase

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ModernSearchBar
import com.mohmmed.mosa.eg.towmmen.presenter.invoic.BarcodeReader
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.purchase.commn.PurchaseItemCard
import kotlinx.coroutines.delay


@Composable
fun PurchaseScreen() {
 // todo
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchaseContent(
    modifier: Modifier = Modifier,
    code: String?,
    onBackClick: () -> Unit,
    onBarcodeClick: () -> Unit
) {

    val productsViewModel: ProductViewModel = hiltViewModel()
    var showBarcodeScan by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.scanner_beep) }
    val products by productsViewModel.products.collectAsState(initial = emptyList())
    var name by remember { mutableStateOf("") }
    var purchaseItem by remember { mutableStateOf(mutableSetOf<Product>())}
    var barcodeValue by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf(barcodeValue.ifEmpty { "" }) }
    var barcode by remember { mutableStateOf(code ?: "") }
    var showInvoiceDialog by remember { mutableStateOf(false) }
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
                .padding(top = topPadding, end = 4.dp, start = 4.dp)
                .verticalScroll(rememberScrollState()),
        ) {

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
                            try{
                                mediaPlayer.start()
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
                    it.name == searchQuery
            }
            purchaseItem.addAll(p)

            items(purchaseItem.toList()){
                PurchaseItemCard(product = it)

            }


            // to do impliment functiloty

        }
    }
}
