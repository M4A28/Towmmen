package com.mohmmed.mosa.eg.towmmen.presenter.profit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency


// todo
@Composable
fun StatisticsScreen() {
    val productsViewModel: ProductViewModel = hiltViewModel()
    val totalProducts by productsViewModel.getProductsCount().collectAsState(initial = 0)
    val averagePrice by productsViewModel.getAveragePrice().collectAsState(initial = 0.0)
    val totalStock by productsViewModel.getTotalStock().collectAsState(initial = 0)
    val inventory by productsViewModel.getAllProductCost().collectAsState(initial = 0.0)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text("Product Statistics", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(stringResource(R.string.basic_statistics), style = MaterialTheme.typography.headlineSmall)
                    Text(stringResource(R.string.total_products, totalProducts ?:0))
                    Text(stringResource(R.string.average_price, formatCurrency(averagePrice ?: 0.0)))
                    Text(stringResource(R.string.total_stock, totalStock?: 0))
                    Text(stringResource(R.string.inventory_value, formatCurrency(inventory ?: 0.0)) )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


