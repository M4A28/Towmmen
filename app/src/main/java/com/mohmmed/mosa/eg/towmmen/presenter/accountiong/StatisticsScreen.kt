package com.mohmmed.mosa.eg.towmmen.presenter.accountiong

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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.mohmmed.mosa.eg.towmmen.data.module.ExpansePerPeriod
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceProfitByPeriod
import com.mohmmed.mosa.eg.towmmen.presenter.accountiong.comman.ExpanseColumnChart
import com.mohmmed.mosa.eg.towmmen.presenter.accountiong.comman.ProfitColumnChart
import com.mohmmed.mosa.eg.towmmen.presenter.accountiong.comman.SimpleStatCard
import com.mohmmed.mosa.eg.towmmen.presenter.accountiong.comman.StatisticItem
import com.mohmmed.mosa.eg.towmmen.presenter.accountiong.comman.TopProductsList
import com.mohmmed.mosa.eg.towmmen.presenter.accountiong.comman.TransactionItem
import com.mohmmed.mosa.eg.towmmen.presenter.expanse.ExpanseViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.invoic.InvoiceViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen() {
    val productsViewModel: ProductViewModel = hiltViewModel()
    val invoiceViewModel: InvoiceViewModel = hiltViewModel()
    val expanseViewModel: ExpanseViewModel = hiltViewModel()
    val totalInvoice by invoiceViewModel.getTotalInvoices().collectAsState(initial = 0.0)
    val minInvoice by invoiceViewModel.getMinInvoice().collectAsState(initial = null)
    val maxInvoice by invoiceViewModel.getMaxInvoice().collectAsState(initial = null)
    val avgInvoice by invoiceViewModel.getInvoiceAvg().collectAsState(initial = 0.0)
    val invoiceProfitByPeriod by invoiceViewModel.getInvoiceProfitByMonth()
        .collectAsState(initial = listOf(InvoiceProfitByPeriod("", 1.0)))
    val totalProducts by productsViewModel.getProductsCount().collectAsState(initial = 0)
    val productsCost by productsViewModel.getAllProductCost().collectAsState(initial = 0.0)
    val averagePrice by productsViewModel.getAveragePrice().collectAsState(initial = 0.0)
    val totalStock by productsViewModel.getTotalStock().collectAsState(initial = 0)
    val inventory by productsViewModel.getAllProductCost().collectAsState(initial = 0.0)
    val topProduct by invoiceViewModel.getTopSellingItem().collectAsState(initial = emptyList())
    val expansePerPeriod by expanseViewModel
        .getExpansePerMonth()
        .collectAsState(initial = listOf(ExpansePerPeriod("", 0.0)))
    val latestTransaction by invoiceViewModel.getAllInvoices().collectAsState(initial = emptyList())

    val statisticItems = listOf(
        StatisticItem(title = stringResource(id = R.string.total_product),
            value = "${ totalProducts ?: 0 }" ),
        StatisticItem(title = stringResource(R.string.total_stock_),
            value = "${totalStock ?: 0}"),

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
            val emptyProfit = listOf(InvoiceProfitByPeriod("", 0.0))
            Text(
                text = stringResource(R.string.total_profit_by_month),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            ProfitColumnChart(average = avgInvoice?: 0.0 , profitByMonth = invoiceProfitByPeriod.ifEmpty { emptyProfit })
        }



        item(span = {
            GridItemSpan(2)
        }) {
            val emptyExpanse = listOf(ExpansePerPeriod("", 0.0))
            Text(
                text = stringResource(R.string.total_expanse_per_month),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            ExpanseColumnChart(average = 0.0,
                expanseByMonth = expansePerPeriod.ifEmpty { emptyExpanse })
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
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ){
                Text(
                    text = stringResource(R.string.recent_transaction),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                latestTransaction.take(4).fastForEach {
                    TransactionItem(invoice = it)
                    Spacer(modifier = Modifier.height(6.dp))

                }
            }

        }
    }
}
