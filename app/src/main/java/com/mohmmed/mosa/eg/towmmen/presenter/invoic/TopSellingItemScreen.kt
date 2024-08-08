package com.mohmmed.mosa.eg.towmmen.presenter.invoic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.TopProduct
import com.mohmmed.mosa.eg.towmmen.ui.theme.CairoFont


@Composable
fun TopSellingItemScreen(){
    val invoiceViewModel: InvoiceViewModel = hiltViewModel()
    val topProduct by invoiceViewModel.getTopSellingItem().collectAsState(initial = emptyList())
    val currentDayTopItems by invoiceViewModel.getTopSellingItemCurrentDay().collectAsState(initial = emptyList())
    val currentMonthTopItems by invoiceViewModel.getTopSellingItemCurrentMonth().collectAsState(initial = emptyList())

    TopSellingItemContent(
        topItems = topProduct,
        currentDayTopItems = currentDayTopItems,
        currentMonthTopItems = currentMonthTopItems
    )
}


@Composable
fun TopSellingItemContent(
    topItems: List<TopProduct>,
    currentDayTopItems: List<TopProduct>,
    currentMonthTopItems: List<TopProduct>
){
    LazyColumn(
        modifier = Modifier
        .fillMaxSize()){

        item{

            TopProductItemCard(
                title = stringResource(R.string.top_selling_product_for_current_day),
                topProducts = currentDayTopItems,
                onItemClick = {}
            )
            Spacer(modifier = Modifier.height(10.dp))

            TopProductItemCard(
                title = stringResource(R.string.top_selling_current_month),
                topProducts = currentMonthTopItems,
                onItemClick = {}
            )

            TopProductItemCard(
                title = stringResource(R.string.top_selling_all_time),
                topProducts = topItems,
                onItemClick = {}
            )
        }
    }
}


@Composable
fun TopProductItemCard(
    title: String,
    modifier: Modifier = Modifier,
    topProducts: List<TopProduct>,
    onItemClick: (TopProduct) -> Unit = {}
){
    var expand by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 6.dp)
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .clickable { expand = !expand },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ){
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontFamily = CairoFont,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(4.dp))
            topProducts.forEach{ item ->
                ListItem(

                    headlineContent = { Text(
                        text = item.productName,
                        fontWeight = FontWeight.Bold,
                        fontFamily = CairoFont,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium
                    ) },
                    trailingContent = {
                        Text(
                            text = "${item.productQuantity}",
                            fontWeight = FontWeight.Bold,
                            fontFamily = CairoFont,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
                HorizontalDivider()
        }

    }

}

