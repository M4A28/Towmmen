package com.mohmmed.mosa.eg.towmmen.presenter.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.comman.NotificationCard
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.navigateToProductDetails
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import com.mohmmed.mosa.eg.towmmen.util.ONE_MONTH
import java.util.Date

@Composable
fun NotificationScreen(
    navyControl: NavHostController
){
    val productViewModel: ProductViewModel = hiltViewModel()
    val products by productViewModel
        .products
        .collectAsState(initial = emptyList())
    // this is temp only not  for production
    val expProducts = products.filter {
        (it.expireDate.time - Date().time) <= ONE_MONTH
    }
    NotificationContent(expProducts = expProducts, navyControl = navyControl)

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationContent(
    modifier: Modifier = Modifier,
    expProducts: List<Product>,
    navyControl: NavHostController
) {
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
                }
            )
        }
    ) { paddingValue ->

        if(expProducts.isNotEmpty()){
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = paddingValue.calculateTopPadding()),
            ) {
                items(expProducts){ product ->
                    NotificationCard(
                        product = product,
                        onCardClick = {
                            navigateToProductDetails(navyControl, it)
                        }
                    )
                }
            }
        }else{
            EmptyScreen(
                massage = stringResource(R.string.no_notifications_yet) ,
                icon = R.drawable.notifications_off,
                alphaAnim = 0.6f
            )
        }
    }

}



