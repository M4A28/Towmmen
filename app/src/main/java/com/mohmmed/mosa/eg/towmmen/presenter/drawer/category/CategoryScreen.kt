package com.mohmmed.mosa.eg.towmmen.presenter.drawer.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Category
import com.mohmmed.mosa.eg.towmmen.presenter.comman.CategoryCard
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen


@Composable
fun CategoryScreen(navController: NavHostController){

    val categoryViewModel: CategoryViewModel = hiltViewModel()
    val categoryList by categoryViewModel.getAllCategory().collectAsState(initial = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }
    CategoryContent(
        onFapClick = {
            //navigateToTab(navController, Route.AddCategoryScreen.route)
                     showAddDialog = true
        },
        onDeleteClick = { categoryViewModel.deleteCategory(it) },
        categories = categoryList
    )

    if(showAddDialog){
        AddCategoryDialog(onAddClick = {
            categoryViewModel.upsertCategory(it)
        }, showDialog = {showAddDialog = it})
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryContent(
    modifier: Modifier = Modifier,
    onFapClick: () -> Unit,
    onDeleteClick: (Category) -> Unit,
    categories: List<Category>
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.category)
                        )

                    }
                },)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onFapClick() },
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }

        }
    ) { paddingValue ->
        val topPadding = paddingValue.calculateTopPadding()
        if(categories.isNotEmpty()){
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = topPadding),
            ) {
                items(categories.size){cat ->
                    CategoryCard(
                        category = categories[cat],
                        onDeleteClick = {
                            onDeleteClick(it)
                        },
                        onClick = {})
                }
            } }else{
            EmptyScreen(
                massage = stringResource(R.string.no_products_yet) ,
                icon = R.drawable.category,
                alphaAnim = 0.6f
            )
        }
    }

}