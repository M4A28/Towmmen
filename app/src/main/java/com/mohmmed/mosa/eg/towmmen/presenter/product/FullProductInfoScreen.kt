package com.mohmmed.mosa.eg.towmmen.presenter.product

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.comman.DeleteConfirmationDialog
import com.mohmmed.mosa.eg.towmmen.presenter.comman.DetailItem
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.Route
import com.mohmmed.mosa.eg.towmmen.util.ONE_DAY
import com.mohmmed.mosa.eg.towmmen.util.PRODUCT_KEY
import com.mohmmed.mosa.eg.towmmen.util.dateToString
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency
import kotlinx.coroutines.launch
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullProductInfoScreen(navController: NavHostController) {
    val productViewModel:ProductViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()

    var showDeleteConfirmation by remember { mutableStateOf(false) }

    navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Product?>(PRODUCT_KEY)?.let {
            val product by productViewModel.getProductById(it.productId).collectAsState(initial = it)
            FullProductInfoContent(
                product = product,
                onClickDeleteClick = {
                    showDeleteConfirmation = true
                },
                onClickEditClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(PRODUCT_KEY, product)
                    navController.navigate(Route.EditProductScreen.route)
                }

            )

            if (showDeleteConfirmation) {
                DeleteConfirmationDialog(
                    title = stringResource(id = R.string.are_you_sure_you_want_to_delete_this_product),
                    massage = stringResource(id = R.string.confirm_deletion),
                    onConfirm = {
                        coroutineScope.launch {
                            productViewModel.deleteProduct(product)
                            navController.popBackStack()
                        }
                    },
                    onDismiss = { showDeleteConfirmation = false }
                )
            }
        }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullProductInfoContent(
    modifier: Modifier = Modifier,
    product: Product,
    onClickDeleteClick:  (Product)  -> Unit,
    onClickEditClick: (Product)  -> Unit
) {

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .clip(RoundedCornerShape(8.dp))
            .shadow(4.dp)
            .background(MaterialTheme.colorScheme.surface),
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ){

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                .data(Uri.parse(product.imagePath))
                .placeholder(R.drawable.image)
                .error(R.drawable.image)
                .build(),
                contentDescription = "",
                modifier = Modifier
                    .padding(4.dp)
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )
            
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 6.dp, end = 16.dp)
            ){
                FilledIconButton(
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    onClick = { onClickEditClick(product) }) {
                    Icon(
                        tint = MaterialTheme.colorScheme.primary,
                        painter = painterResource(id = R.drawable.edit ),
                        contentDescription = null
                    )
                }

                Spacer(Modifier.width(8.dp))

                // delete
                FilledIconButton(
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    onClick = { onClickDeleteClick(product) }) {
                    Icon(
                        tint = MaterialTheme.colorScheme.error,
                        painter = painterResource(id = R.drawable.delete ),
                        contentDescription = null
                    )
                }
            }


        }

        Spacer(modifier = Modifier.height(4.dp))

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
        ) {

            DetailItem(
                icon = R.drawable.bag,
                label = stringResource(R.string.product_name_),
                value = product.name
            )


            Spacer(modifier = Modifier.height(6.dp))

            DetailItem(
                icon = R.drawable.description,
                label = stringResource(R.string.description),
                value = product.description
            )
            Spacer(modifier = Modifier.height(6.dp))

            DetailItem(
                icon = R.drawable.barcode,
                label = stringResource(R.string.producat_barcode),
                value = product.barcode
            )
            Spacer(modifier = Modifier.height(6.dp))

            DetailItem(
                icon = R.drawable.cash,
                label = stringResource(id = R.string.cost_),
                value = formatCurrency(product.cost)
            )
            Spacer(modifier = Modifier.height(6.dp))

            DetailItem(
                icon = R.drawable.tag,
                label = stringResource(id = R.string.price),
                value = formatCurrency(product.price)
            )
            Spacer(modifier = Modifier.height(6.dp))


            DetailItem(
                icon = R.drawable.box,
                label = stringResource(id = R.string.quantity_),
                value = "${product.stockQuantity}  ${product.unit}",
            )
            Spacer(modifier = Modifier.height(6.dp))

            DetailItem(
                icon = R.drawable.calendar_month,
                label = stringResource(id = R.string.manf_date),
                value = dateToString(product.manufactureDate, "yyyy/MM/dd"),
            )
            Spacer(modifier = Modifier.height(6.dp))

            DetailItem(
                icon = R.drawable.date,
                label = stringResource(id = R.string.exp_date),
                value = dateToString(product.expireDate, "yyyy/MM/dd"),
            )
            Spacer(modifier = Modifier.height(6.dp))

            DetailItem(
                icon = R.drawable.calendar_month,
                label =  stringResource(id = R.string.days_until_expire_),
                value = if(product.expireDate.time - Date().time > 0)
                        ((product.expireDate.time - Date().time) / ONE_DAY).toString()
                else stringResource(id = R.string.exp_product),
            )
            Spacer(modifier = Modifier.height(6.dp))

            if(product.category.isNotEmpty()){
                DetailItem(
                    icon = R.drawable.category,
                    label = String.format(stringResource(id = R.string.category)),
                    value = product.category
                )
            }
            Spacer(modifier = Modifier.height(6.dp))

        }
    }
}

