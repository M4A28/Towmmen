package com.mohmmed.mosa.eg.towmmen.presenter.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.ExpansePerMonth
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceProfitByMonth
import com.mohmmed.mosa.eg.towmmen.presenter.home.comman.TopProductExplainedCard
import com.mohmmed.mosa.eg.towmmen.presenter.profit.comman.ExpanseColumnChart
import com.mohmmed.mosa.eg.towmmen.presenter.profit.comman.ProfitColumnChart
import com.mohmmed.mosa.eg.towmmen.presenter.profit.comman.SimpleStatCard
import com.mohmmed.mosa.eg.towmmen.presenter.profit.comman.StatisticItem
import com.mohmmed.mosa.eg.towmmen.presenter.profit.comman.TransactionItem
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency


@Composable
fun HomeScreen() {
    val homeViewModel: HomeViewModel = hiltViewModel()

    val totalInvoice by homeViewModel.totalInvoices.collectAsState()
    val avgInvoice by homeViewModel.avgInvoicePerMonth.collectAsState()
    val invoiceProfitByMonth by homeViewModel.invoicePerMonth.collectAsState()
    val totalProducts by homeViewModel.productCount.collectAsState()
    val productsCost by homeViewModel.productsCost.collectAsState()
    val expanseAvg by homeViewModel.avgExpansePerMonth.collectAsState()
    val totalCustomers by homeViewModel.customerCount.collectAsState()
    val topProduct by homeViewModel.mostTopSellingProduct.collectAsState()
    val expansePerMonth by homeViewModel.expansePerMonthItems.collectAsState()
    val latestTransaction by homeViewModel.invoices.collectAsState()
    val last4Transaction by remember {
        mutableStateOf(latestTransaction.take(4))
    }
    val context = LocalContext.current

    val statisticItems by remember {
        mutableStateOf(listOf(
            StatisticItem(title = context.getString(R.string.total_product),
                value = totalProducts.toString() ),

            StatisticItem(title = context.getString(R.string.total_customer),
                value = "${totalCustomers?: 0}"),

            StatisticItem(title = context.getString(R.string.total_invoice_Value),
                value = formatCurrency(totalInvoice?: 0.0)),

            StatisticItem(title = context.getString(R.string.produts_cost),
                value = formatCurrency(productsCost?: 0.0)),
        ))
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        columns = GridCells.Fixed(2)
    ) {

        items(statisticItems, key = {
            it.title
        }){ item ->
            SimpleStatCard(
                modifier = Modifier.padding(8.dp),
                title = item.title,  value = item.value
            )
        }
        item(span = { GridItemSpan(2) }) {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item(span = {
            GridItemSpan(2)
        }) {
            Column(modifier = Modifier.padding(10.dp)) {
                val emptyProfit = listOf(InvoiceProfitByMonth("", 0.0))
                val emptyExpanse = listOf(ExpansePerMonth("", 0.0))

                Text(
                    text = stringResource(R.string.total_profit_by_month),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                ProfitColumnChart(average = avgInvoice?: 0.0 , profitByMonth = invoiceProfitByMonth.ifEmpty { emptyProfit })

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.total_expanse_per_month),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                ExpanseColumnChart(average = expanseAvg?: 0.0,
                    expanseByMonth = expansePerMonth.ifEmpty { emptyExpanse })

            }
        }

        item(span = {
            GridItemSpan(2)
        }) {

            TopProductExplainedCard(top = topProduct, total = totalInvoice?: 0.0)

      /*      TopProductsList(
                modifier = Modifier.padding(10.dp),
                title = stringResource(id = R.string.top_products),
                topProductList = topProduct
            )*/

        }

        item(span = {
            GridItemSpan(2)
        }) {
            Spacer(modifier = Modifier.height(24.dp))

            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ){
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {

                    Text(
                        text = stringResource(R.string.recent_transaction),
                        fontWeight = FontWeight.Bold,
                    )
                    if(latestTransaction.isNotEmpty()){

                        last4Transaction.fastForEach {
                            TransactionItem(invoice = it)
                            Spacer(modifier = Modifier.height(6.dp))

                        }
                    }else{
                        Text(
                            text = stringResource(R.string.no_transcation_yet),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

        }


    }
}
