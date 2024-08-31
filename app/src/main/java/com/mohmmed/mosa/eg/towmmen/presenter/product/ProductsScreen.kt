package com.mohmmed.mosa.eg.towmmen.presenter.product

import android.annotation.SuppressLint
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.comman.CustomDropDownMenu
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ModernSearchBarWithSuggestions
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ProductCard2
import com.mohmmed.mosa.eg.towmmen.presenter.drawer.category.CategoryViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.Route
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.navigateToProductDetails
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.navigateToScreen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductsScreen(
    navController: NavHostController
){
    val productViewModel: ProductViewModel = hiltViewModel()
    val products by productViewModel
        .products
        .collectAsState(initial = emptyList())

    val categoryViewModel: CategoryViewModel = hiltViewModel()
    val categoryList by categoryViewModel.getAllCategory().collectAsState(initial = emptyList())

    ProductsContent(
        products = products,
        onProductClick = { product -> navigateToProductDetails(navController, product) },
        onFapClick = { navigateToScreen(navController, Route.AddProductScreen.route) },
        categories = categoryList.map{ it.category}
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductsContent(
    modifier: Modifier = Modifier,
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    categories: List<String> = listOf(),
    onFapClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var showEditPriceDialog by remember { mutableStateOf(false) }
    if(showEditPriceDialog){
        ModifyProductPriceDialog(
            onDismissRequest = {},
            onConfirm = {a,b,c ->})
    }
    Scaffold(
        topBar = {
                  TopAppBar(
                      title = {
                          Box(
                              modifier = Modifier.fillMaxWidth(),
                              contentAlignment = Alignment.Center
                          ) {
                              Text(text = stringResource(id = R.string.products))

                          }

                      },
                      actions = {
                          CustomDropDownMenu(
                              actionOne = stringResource(R.string.increase_decrees_price),
                              actionTwo = stringResource(R.string.import_product_from_csv_file),
                              onActionOne = {
                                            showEditPriceDialog = true

                              },
                              onActionTwo = {
                                  // todo
                              }
                          )
                      })
         },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onFapClick()
                }
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }

        }
    ) { paddingValue ->
        val topPadding = paddingValue.calculateTopPadding()
        if(products.isNotEmpty()){
            LazyVerticalStaggeredGrid(
                modifier = modifier.fillMaxWidth(),
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp)
            ) {

                item(span = StaggeredGridItemSpan.FullLine ){
                    Column(modifier = Modifier.padding(top =topPadding)) {

                        ModernSearchBarWithSuggestions(
                            searchQuery = searchQuery,
                            onSearchQueryChange = { searchQuery = it },
                            suggestions = products.map { it.name }.filter{ it.contains(searchQuery, ignoreCase = true) },
                            onSuggestionSelected = {searchQuery = it },
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        // Filter Chips
                        Row(
                            modifier = Modifier
                                .fillMaxWidth().horizontalScroll(rememberScrollState())
                                .padding(bottom = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            categories.forEach { category ->
                                FilterChip(
                                    selected = category == selectedCategory,
                                    onClick = { selectedCategory = category },
                                    modifier = Modifier.height(32.dp),
                                    label = { Text(category, fontWeight = FontWeight.Medium) }
                                )
                            }
                        }
                    }
                }
                items(
                    products.filter{it.category == selectedCategory ||
                            it.name.contains(searchQuery, true) ||
                                   it.barcode == searchQuery},
                    key = { it.productId }
                ){ product ->
                    ProductCard2(
                        product = product,
                        onClick = {onProductClick(product)}
                    )
                }
            }
        }else{
            EmptyScreen(
                massage = stringResource(R.string.no_products_yet) ,
                icon = R.drawable.box,
                alphaAnim = 0.6f
            )
        }
    }

}
