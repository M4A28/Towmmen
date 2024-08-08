package com.mohmmed.mosa.eg.towmmen.presenter.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.comman.CustomerCard
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ProductCard
import com.mohmmed.mosa.eg.towmmen.presenter.comman.SimpleInfo
import com.mohmmed.mosa.eg.towmmen.presenter.comman.SingleInfoCard
import com.mohmmed.mosa.eg.towmmen.presenter.customer.CustomerViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.invoic.InvoiceViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.Route
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.navigateToCustomerDetails
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.navigateToProductDetails
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.navigateToScreen
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.navigateToTab
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import com.mohmmed.mosa.eg.towmmen.ui.theme.BlueShades
import com.mohmmed.mosa.eg.towmmen.ui.theme.CairoFont
import com.mohmmed.mosa.eg.towmmen.ui.theme.GreenShades
import com.mohmmed.mosa.eg.towmmen.ui.theme.OrangeShades
import com.mohmmed.mosa.eg.towmmen.ui.theme.RedShades

@Composable
fun HomeScreen(navController: NavHostController) {
    val productViewModel: ProductViewModel = hiltViewModel()
    val productsCost by productViewModel
        .getAllProductCost().collectAsState(initial = 0.0)
    val invoiceViewModel: InvoiceViewModel = hiltViewModel()
    val totalInvoice by invoiceViewModel.getAllInvoiceProfit().collectAsState(initial = 0.0)
    val products by productViewModel.products.collectAsState(initial = emptyList())
    val productsCount by productViewModel.getProductsCount().collectAsState(initial = 0)
    val customerViewModel: CustomerViewModel = hiltViewModel()
    val customers by customerViewModel.getAllCustomer().collectAsState(initial = emptyList())
    val customerCount by customerViewModel
        .getCustomerCount().collectAsState(initial = 0)

    val infos = listOf(
        SimpleInfo(
            title = stringResource(R.string.total_product),
            subtitle = "$productsCount",
            icon = R.drawable.bag,
            gradientColors = OrangeShades

        ) ,
        SimpleInfo(
            title = stringResource(R.string.total_customer),
            subtitle = "$customerCount",
            icon = R.drawable.person,
            gradientColors = RedShades

        ),
        SimpleInfo(
            title = stringResource(R.string.total_product_cost),
            subtitle = String.format("%.2f", productsCost?: 0.0),
            icon = R.drawable.cash,
            gradientColors = BlueShades

        ),
        SimpleInfo(
            title = stringResource(R.string.total_invoice_Value),
            subtitle = String.format("%.2f", totalInvoice?: 0.0),
            icon = R.drawable.cash,
            gradientColors = GreenShades

        )

    )
    HomeContent(
        navController = navController,
        infoes = infos,
        latestProduct = products,
        onProductClick = {
            navigateToProductDetails(navController, it)
        },
        newCustomers = customers,
        onCustomerClick = {
            navigateToCustomerDetails(navController, it)
        })
}

@Composable
fun HomeContent(
    navController: NavHostController,
    infoes: List<SimpleInfo>,
    latestProduct: List<Product>,
    onProductClick: (Product) -> Unit = {},
    newCustomers: List<Customer>,
    onCustomerClick: (Customer) -> Unit = {}
) {
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        item{
            LazyRow {
                items(infoes){
                    Box(
                        modifier = Modifier.padding(end = 8.dp, start = 8.dp)
                    ){

                        SingleInfoCard(
                            title = it.title,
                            subtitle = it.subtitle,
                            icon = it.icon,
                            gradientColors = it.gradientColors,
                            onCardClick = {
                                when(it.icon){
                                    R.drawable.bag -> navigateToTab(navController, Route.ProductScreen.route)
                                    R.drawable.person -> navigateToTab(navController, Route.CustomerScreen.route)
                                    R.drawable.cash -> navigateToScreen(navController, Route.ProfitScreen.route)
                                }
                            })
                    }
                }
            }
        }

        // New Customer Section
        item {
            Spacer(modifier = Modifier.height(8.dp))
            if(newCustomers.isNotEmpty()){
                Text(
                    fontFamily = CairoFont,
                    text = stringResource(R.string.new_customer),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow {
                    items(newCustomers.take(3), key = {
                        it.registrationDate
                    }) { customer ->
                        CustomerCard(
                            customer = customer, onClick = {
                                onCustomerClick(customer)
                            })
                    }
                }
            }else{
                EmptyScreen(massage = stringResource(id = R.string.no_customers_yet),
                    icon = R.drawable.person,
                    alphaAnim = 0.3f )
            }
        }

        // new Products Section
        item {
            if(latestProduct.isNotEmpty()){

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = stringResource(R.string.latest_add_products),
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = CairoFont,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow {
                    items(latestProduct.take(3)) { product ->
                        ProductCard(product = product, onClick = {
                            onProductClick(product)
                        })
                    }
                }
            }else{
                EmptyScreen(
                    massage = stringResource(id = R.string.no_products_yet),
                    icon = R.drawable.box,
                    alphaAnim = 0.3f
                )
            }
        }
    }
}




