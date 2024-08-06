package com.mohmmed.mosa.eg.towmmen.presenter.notifications

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.comman.NotificationCard
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.navigateToProductDetails
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import com.mohmmed.mosa.eg.towmmen.ui.theme.Green40
import com.mohmmed.mosa.eg.towmmen.ui.theme.Red40
import com.mohmmed.mosa.eg.towmmen.ui.theme.Yellow40
import com.mohmmed.mosa.eg.towmmen.util.ONE_DAY
import com.mohmmed.mosa.eg.towmmen.util.ONE_MONTH
import com.mohmmed.mosa.eg.towmmen.util.ONE_WEEK
import com.mohmmed.mosa.eg.towmmen.util.TOW_WEEKS
import java.util.Date

@Composable
fun NotificationContent(
    modifier: Modifier = Modifier,
    expProducts: List<Product>,
    navyControl: NavHostController
) {
    if(expProducts.isNotEmpty()){

        LazyColumn(
            modifier = modifier.fillMaxSize(),
        ) {
            items(expProducts.size){/*product ->*/
                var product = expProducts[it]
                var expDate = product.expireDate
                NotificationCard(
                    title = title(expDate),
                    action = action(expDate),
                    product = product,
                    indicatorColor = indicatorColor(expDate),
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

@Composable
fun title(expDate: Date): String{
    val currentDate = Date()
    val value = expDate.time - currentDate.time
    val day = value / ONE_DAY

    return when {
        value <= 0 -> stringResource(R.string.exp_product)
        value <= ONE_DAY -> stringResource(R.string.wring_exp_product2)
        value <= TOW_WEEKS -> String.format(stringResource(R.string.wring_exp_product_in_days), day)
        value <= ONE_MONTH -> String.format(stringResource(R.string.wring_exp_product_in_days), day)
        else -> stringResource(R.string.wring_exp_product)
    }
}

@Composable
fun action(date: Date): String{
    val currentDate = Date()
    val value = date.time - currentDate.time
    return when {
        value >= TOW_WEEKS -> stringResource(R.string.exp_action_3)
        value <= ONE_DAY -> stringResource(R.string.exp_action_4)
        value <= ONE_WEEK -> stringResource(R.string.exp_action_1)
        value <= TOW_WEEKS -> stringResource(R.string.exp_action_2)
        else -> stringResource(R.string.exp_action_4)
    }
}

fun indicatorColor(date: Date): Color {
    val currentDate = Date()
    val value = date.time - currentDate.time
    return when {
        value <= ONE_DAY -> Red40
        value <= ONE_WEEK -> Yellow40
        value <= TOW_WEEKS -> Green40
        else -> Color.Gray
    }
}


