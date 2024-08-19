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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.ExpansePerMonth
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceProfitByMonth
import com.mohmmed.mosa.eg.towmmen.presenter.customer.CustomerViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.expanse.ExpanseViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.invoic.InvoiceViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.profit.comman.CustomColumnChart
import com.mohmmed.mosa.eg.towmmen.presenter.profit.comman.CustomColumnChart2
import com.mohmmed.mosa.eg.towmmen.presenter.profit.comman.SimpleStatCard
import com.mohmmed.mosa.eg.towmmen.presenter.profit.comman.StatisticItem
import com.mohmmed.mosa.eg.towmmen.presenter.profit.comman.TopProductsList
import com.mohmmed.mosa.eg.towmmen.presenter.profit.comman.TransactionItem
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency


@Composable
fun HomeScreen() {
    val productsViewModel: ProductViewModel = hiltViewModel()
    val invoiceViewModel: InvoiceViewModel = hiltViewModel()
    val customerViewModel: CustomerViewModel = hiltViewModel()
    val expanseViewModel: ExpanseViewModel = hiltViewModel()
    val totalInvoice by invoiceViewModel.getTotalInvoices().collectAsState(initial = 0.0)
    val avgInvoice by invoiceViewModel.avgInvoicePerMonth.collectAsState()
    val invoiceProfitByMonth by invoiceViewModel.getInvoiceProfitByMonth()
        .collectAsState(initial = listOf(InvoiceProfitByMonth("", 1.0)))
    val totalProducts by productsViewModel.getProductsCount().collectAsState(initial = 0)
    val productsCost by productsViewModel.getAllProductCost().collectAsState(initial = 0.0)
    val expanseAvg by expanseViewModel.avgExpansePerMonth.collectAsState()
    val totalCustomers by customerViewModel.getCustomerCount().collectAsState(initial = 0)
    val topProduct by invoiceViewModel.getTopSellingItem().collectAsState(initial = emptyList())
    val expansePerMonth by expanseViewModel
        .getExpansePerMonth()
        .collectAsState(initial = listOf(ExpansePerMonth("", 0.0)))
    val latestTransaction by invoiceViewModel.getAllInvoices()
        .collectAsState(initial = emptyList())


    val statisticItems = listOf(
        StatisticItem(title = stringResource(id = R.string.total_product),
            value = totalProducts.toString() ),
        StatisticItem(title = stringResource(R.string.total_customer),
            value = "${totalCustomers?: 0}"),

        StatisticItem(title = stringResource(R.string.total_invoice_Value),
            value = formatCurrency(totalInvoice?: 0.0)),
        StatisticItem(title = stringResource(R.string.produts_cost),
            value = formatCurrency(productsCost?: 0.0)),
    )

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
                CustomColumnChart(average = avgInvoice?: 0.0 , profitByMonth = invoiceProfitByMonth.ifEmpty { emptyProfit })

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.total_expanse_per_month),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                CustomColumnChart2(average = expanseAvg?: 0.0,
                    expanseByMonth = expansePerMonth.ifEmpty { emptyExpanse })

            }
        }



        item(span = {
            GridItemSpan(2)
        }) {

            TopProductsList(
                modifier = Modifier.padding(10.dp),
                title = stringResource(id = R.string.top_products),
                topProductList = topProduct
            )

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

                        latestTransaction.take(4).fastForEach {
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
