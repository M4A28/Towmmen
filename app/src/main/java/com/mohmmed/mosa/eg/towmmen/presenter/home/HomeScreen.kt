package com.mohmmed.mosa.eg.towmmen.presenter.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.mohmmed.mosa.eg.towmmen.presenter.accountiong.comman.TransactionItem
import com.mohmmed.mosa.eg.towmmen.presenter.home.comman.TopProductExplainedCard
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
    val expansePerMonth by homeViewModel.expansePerMonth.collectAsState()
    val latestTransaction by homeViewModel.invoices.collectAsState()
    val context = LocalContext.current
    val emptyProfit by remember {
        mutableStateOf( listOf(InvoiceProfitByPeriod("", 0.0)))}
    val emptyExpanse by remember {
        mutableStateOf( listOf(ExpansePerPeriod("", 0.0)))}
    val last4Transaction by remember {
        mutableStateOf(latestTransaction.take(4))
    }


    val customersStatics by remember {
        mutableStateOf(listOf(            
            StatisticItem(title = context.getString(R.string.total_customer),
            value = "${totalCustomers?: 0}"),

            StatisticItem(title = context.getString(R.string.total_invoice_Value),
                value = formatCurrency(totalInvoice?: 0.0)),))
    }
    val productsStatics by remember { mutableStateOf(listOf(
        StatisticItem(title = context.getString(R.string.total_product),
            value = totalProducts.toString() ),
        StatisticItem(title = context.getString(R.string.produts_cost),
            value = formatCurrency(productsCost?: 0.0)),))
    }
    

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {

        // customers ststics
        item{
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                customersStatics.fastForEach {
                    SimpleStatCard(modifier = Modifier
                            .weight(1f)
                            .padding(4.dp),
                        title = it.title,
                        value = it.value)
                }
            }

        }

        // products statics
        item{
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                productsStatics.fastForEach {
                    SimpleStatCard(modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                        title = it.title,
                        value = it.value)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

        }


        item {
            Column(modifier = Modifier.padding(10.dp)) {

                Text(
                    text = stringResource(R.string.invoices_total_ber_month),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                ProfitColumnChart(average = avgInvoice?: 0.0 ,

                    profitByMonth = invoiceProfitByMonth.ifEmpty { emptyProfit })

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.expanse_total_ber_month),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                ExpanseColumnChart(average = expanseAvg?: 0.0,
                    expanseByMonth = expansePerMonth.ifEmpty { emptyExpanse })

            }
        }

        item{
            TopProductExplainedCard(top = topProduct, total = totalInvoice?: 0.0)
        }

        item{
            Spacer(modifier = Modifier.height(24.dp))

            ElevatedCard(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            ){
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {

                    Text(
                        text = stringResource(R.string.recent_transaction),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
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
