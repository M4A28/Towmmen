package com.mohmmed.mosa.eg.towmmen.presenter.drawer.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.domin.module.Category
import com.mohmmed.mosa.eg.towmmen.presenter.comman.CustomTextFiled


@Composable
fun AddCategoryScreen(navController: NavHostController){
    val categoryViewModel: CategoryViewModel = hiltViewModel()
    AddCategoryContent(
        onAddClick = {
            categoryViewModel.upsertCategory(it)
        },
        onBackClick = {
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryContent(
    modifier: Modifier = Modifier,
    onAddClick: (Category) -> Unit,
    onBackClick: () -> Unit = {}
){
    var category by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp)),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClick()
                    }){
                        Icon(
                            tint = MaterialTheme.colorScheme.onPrimary,
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "ArrowBack"
                        )
                    }
                },
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(id = R.string.add_new_cat))
                    }

                })
        }
    ) { padding ->
        val topPadding = padding.calculateTopPadding()

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = topPadding, end = 4.dp, start = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            CustomTextFiled(
                value = category,
                onValueChange = { category = it },
                label = stringResource(id = R.string.product_name_),
                leadingIcon = R.drawable.shopping
            )

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    if(category.isNotEmpty()){
                        onAddClick(
                            Category(
                                id = 0, category = category.trim())
                        )
                        // clearData
                        category = ""
                    }
                },
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add "
                )
                Text(
                    text = stringResource(id = R.string.add_new_cat),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }


}