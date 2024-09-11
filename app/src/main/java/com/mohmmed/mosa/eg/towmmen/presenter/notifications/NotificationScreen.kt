package com.mohmmed.mosa.eg.towmmen.presenter.notifications

import android.Manifest
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.comman.CustomDropDownMenu
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import com.mohmmed.mosa.eg.towmmen.util.ONE_MONTH
import com.mohmmed.mosa.eg.towmmen.util.exportExpProductsToTxt
import com.mohmmed.mosa.eg.towmmen.util.exportOutOfStockProductsToTxt
import kotlinx.coroutines.launch
import java.util.Date

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationScreen(
    navHostController: NavHostController
){
    val productViewModel: ProductViewModel = hiltViewModel()
    val products by productViewModel.products.collectAsState(initial = emptyList())
    var storagePermissionGranted by remember { mutableStateOf(false) }
    val storagePermissionState =
        rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts
            .RequestPermission()
    ) { storagePermissionGranted = it }
    val context = LocalContext.current
    // this is temp only not  for production
    val expProducts = products.filter {
        (it.expireDate.time - Date().time) <= ONE_MONTH
    }

    val outOfStockProducts = products.filter {
       it.stockQuantity <= 0
    }
    LaunchedEffect(storagePermissionState) {
        if (!storagePermissionState.status.isGranted
            && storagePermissionState.status.shouldShowRationale
        ) {
           Toast.makeText(context,
               context.getString(R.string.permission_is_important),
               Toast.LENGTH_LONG).show()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }
    NotificationContent(
        expProducts = expProducts,
        outOfStockProducts = outOfStockProducts,
        navHostController = navHostController,
        onExportExpProductClick = { expProductList ->
            if (expProductList.isNotEmpty() && storagePermissionGranted) {
                val expProducts = exportExpProductsToTxt(expProductList, context)
                Intent(Intent.ACTION_SEND).also {
                    it.setType("text/plain")
                    it.putExtra(Intent.EXTRA_TEXT, expProducts)
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(Intent.createChooser(it, context.getString(R.string.share_to)))
                    }
                }

            } else{
                Toast.makeText(context,
                    context.getString(R.string.no_exp_product_to_export),
                    Toast.LENGTH_LONG).show()
            }

        },
        onExportOutOfStockProductClick = { outOfStockProductsList ->
            if (outOfStockProductsList.isNotEmpty()) {
                val outOfStockProducts = exportOutOfStockProductsToTxt(outOfStockProductsList, context)
                Intent(Intent.ACTION_SEND).also {
                    it.setType("text/plain")
                    it.putExtra(Intent.EXTRA_TEXT, outOfStockProducts)
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(Intent.createChooser(it, context.getString(R.string.share_to)))
                    }
                }

            }else{
                Toast.makeText(context,
                    context.getString(R.string.no_product_found_to_export),
                    Toast.LENGTH_LONG).show()
            }
        },
    )

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NotificationContent(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    expProducts: List<Product>,
    outOfStockProducts: List<Product>,
    onExportExpProductClick: (List<Product>) -> Unit,
    onExportOutOfStockProductClick: (List<Product>) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { NotificationTap.entries.size })
    val selectedTabIndex by remember{
        derivedStateOf { pagerState.currentPage }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.notifications)
                        )
                    }
                },
                actions = {
                    CustomDropDownMenu(
                        actionOne = stringResource(R.string.export_out_of_stock_product),
                        onActionOne = {onExportOutOfStockProductClick(outOfStockProducts)},
                        actionTwo = stringResource(R.string.export_exp_product),
                        onActionTwo = {onExportExpProductClick(expProducts)})
                }
            )
        }
    ) { paddingValue ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = paddingValue.calculateTopPadding())
        ) {

            TabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = selectedTabIndex) {
                NotificationTap.entries.forEachIndexed { index, currentTab ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(currentTab.ordinal)
                            }
                        },
                        text = {
                            Text(
                                text = stringResource(id = currentTab.text),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        icon = {
                            Icon(painter = painterResource(id = currentTab.icon),
                                contentDescription = null)
                        }
                    )
                }

            }
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = pagerState) {
                when(selectedTabIndex){
                    0 -> ExpProductContent(expProducts =  expProducts,
                navHostController = navHostController)
                    1 -> OutOfStockContent(outOfStockProducts =  outOfStockProducts,
                        navHostController = navHostController)
                }
            }
        }
    }

}



