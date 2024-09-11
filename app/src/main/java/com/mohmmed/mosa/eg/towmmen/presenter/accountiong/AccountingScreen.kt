package com.mohmmed.mosa.eg.towmmen.presenter.accountiong

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
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.presenter.expanse.ExpanseScreen
import com.mohmmed.mosa.eg.towmmen.presenter.invoic.InvoiceScreen
import com.mohmmed.mosa.eg.towmmen.presenter.invoic.TopSellingItemScreen
import com.mohmmed.mosa.eg.towmmen.presenter.purchase.PurchaseScreen
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AccountingScreen(navController: NavHostController){
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { Tabs.entries.size })
    val selectedTabIndex by remember{ derivedStateOf { pagerState.currentPage } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) { Text(
                        text = stringResource(id = R.string.accounting),
                    ) }
                }
            )
        }
    ) { paddingVale ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingVale.calculateTopPadding())
        ) {

            ScrollableTabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = selectedTabIndex) {
                Tabs.entries.forEachIndexed { index, currentTab ->
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
                    0 -> InvoiceScreen(navController)
                    1 -> PurchaseScreen()
                    2 -> TopSellingItemScreen()
                    3 -> ExpanseScreen(navController = navController)
                }
            }
        }
    }
}