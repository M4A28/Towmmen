package com.mohmmed.mosa.eg.towmmen.presenter.purchase

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.presenter.comman.CustomTextFiled
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ProductCard
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import com.mohmmed.mosa.eg.towmmen.ui.theme.CairoFont


@Composable
fun PurchaseScreen() {

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchaseContent(
    modifier: Modifier = Modifier,
    code: String?,
    onBackClick: () -> Unit,
    onBarcodeClick: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var barcode by remember { mutableStateOf(code ?: "") }
    val productsViewModel: ProductViewModel = hiltViewModel()
    val products by productsViewModel.products.collectAsState(initial = emptyList())
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClick()
                    }) {
                        Icon(
                            tint = MaterialTheme.colorScheme.onPrimary,
                            imageVector = Icons.Default.ArrowBack,
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {

                CustomTextFiled(
                    value = name,
                    onValueChange = { name = it },
                    label = stringResource(id = R.string.product_name_),
                    leadingIcon = R.drawable.shopping
                )
                Spacer(modifier = Modifier.height(4.dp))
                CustomTextFiled(
                    value = barcode,
                    onValueChange = {
                        barcode = it
                    },
                    onIconClick = { onBarcodeClick() },
                    label = stringResource(R.string.producat_barcode),
                    leadingIcon = R.drawable.barcode
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                if (products.isNotEmpty()) {

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.latest_add_products),
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = CairoFont,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow {
                        items(products.take(3)) { product ->
                            ProductCard(product = product, onClick = {
                            })
                        }
                    }
                } else {
                    EmptyScreen(
                        massage = stringResource(id = R.string.no_products_yet),
                        icon = R.drawable.box,
                        alphaAnim = 0.3f
                    )
                }


            }

        }
    }
}
