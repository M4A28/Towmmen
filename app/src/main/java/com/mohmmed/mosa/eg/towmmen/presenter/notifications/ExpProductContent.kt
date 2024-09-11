package com.mohmmed.mosa.eg.towmmen.presenter.notifications

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.comman.NotificationCard
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.navigateToProductDetails

@Composable
fun ExpProductContent(
    modifier: Modifier = Modifier,
    expProducts: List<Product>,
    navHostController: NavHostController
){
    if(expProducts.isNotEmpty()){
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
        ) {
            items(expProducts){ product ->
                NotificationCard(
                    product = product,
                    onCardClick = {
                        navigateToProductDetails(navHostController, it)
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