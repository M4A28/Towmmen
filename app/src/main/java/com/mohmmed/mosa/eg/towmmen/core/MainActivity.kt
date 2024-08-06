package com.mohmmed.mosa.eg.towmmen.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.NavGraph
import com.mohmmed.mosa.eg.towmmen.presenter.test.TestViewModel
import com.mohmmed.mosa.eg.towmmen.ui.theme.TowmmenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>()
    val testVm by  viewModels<TestViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TowmmenTheme {
                // this for tet only
        /*        if(viewModel.festLaunch){
                    testVm.makeFakeCustomer(10)
                    testVm.makeFakeProduct(35)
                }*/
                val startDestination = viewModel.startDestination
                NavGraph(startDestination = startDestination)
            }
        }
    }
}
