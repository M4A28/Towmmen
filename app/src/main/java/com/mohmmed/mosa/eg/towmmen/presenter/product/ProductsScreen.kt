package com.mohmmed.mosa.eg.towmmen.presenter.product

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ModernSearchBar
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ProductCard2
import com.mohmmed.mosa.eg.towmmen.presenter.drawer.category.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductsScreen(
    onProductClick: (Product) -> Unit,
    onFapClick: () -> Unit
){
    val productViewModel: ProductViewModel = hiltViewModel()
    val products by productViewModel
        .products
        .collectAsState(initial = emptyList())

    val categoryViewModel: CategoryViewModel = hiltViewModel()
    val categoryList by categoryViewModel.getAllCategory().collectAsState(initial = emptyList())

    ProductsContent(
        products = products,
        onProductClick = {onProductClick(it)},
        onFapClick = {onFapClick() },
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
    var selectedCategory by remember { mutableStateOf("All") }
    Scaffold(
        topBar = {
                  TopAppBar(
                      colors = TopAppBarDefaults.topAppBarColors(
                          containerColor = MaterialTheme.colorScheme.primary,
                          titleContentColor = MaterialTheme.colorScheme.onPrimary,
                      ),
                      title = {
                          Box(
                              modifier = Modifier.fillMaxWidth(),
                              contentAlignment = Alignment.Center
                          ) {
                              Text(text = stringResource(id = R.string.products))

                          }

                      })
         },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onFapClick()
                },
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(4.dp)
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
            LazyVerticalGrid(
                modifier = modifier.fillMaxWidth(),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp)
            ) {

                item(span = { GridItemSpan(2) }){
                    Column(modifier = Modifier
                        .padding(top =topPadding)
                      ) {
                        ModernSearchBar(
                            searchQuery = searchQuery,
                            onSearchQueryChange = { searchQuery = it },
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        // Filter Chips
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(bottom = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            categories.forEach { category ->
                                FilterChip(
                                    selected = category == selectedCategory,
                                    onClick = { selectedCategory = category },
                                    modifier = Modifier.height(32.dp),
                                    colors = FilterChipDefaults.filterChipColors(
                                        containerColor = Color(0xFFF3F4F6),
                                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                                        selectedLabelColor = Color.White,
                                    ),
                                    label = { Text(category, fontWeight = FontWeight.Medium) }
                                )
                            }
                        }
                    }
                }
                items(
                    products.filter {
                        (selectedCategory == "All" || it.category == selectedCategory) &&
                                it.name.contains(searchQuery, ignoreCase = true)
                    }.size){product ->
                    ProductCard2(
                        product = products[product],
                        onClick = {onProductClick(products[product])}
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
