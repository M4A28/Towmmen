package com.mohmmed.mosa.eg.towmmen.presenter.product

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mohmmed.mosa.eg.towmmen.domin.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ProductCard

@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    products: List<Product>,
    onProductClick: (Product) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(products.size){product ->
            ProductCard(
                product = products[product],
                onClick = {onProductClick(products[product])}
            )
        }

    }

}