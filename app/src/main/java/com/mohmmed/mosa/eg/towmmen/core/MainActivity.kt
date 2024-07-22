package com.mohmmed.mosa.eg.towmmen.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.customer.CustomerViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.AppNavigator
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import com.mohmmed.mosa.eg.towmmen.ui.theme.TowmmenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val customerViewModel: CustomerViewModel = hiltViewModel()
            val productViewModel: ProductViewModel = hiltViewModel()
            TowmmenTheme {
                AppNavigator()
            }
        }
    }
}
