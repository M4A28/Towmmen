package com.mohmmed.mosa.eg.towmmen.presenter.product

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.domin.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.comman.TextWithIcon
import com.mohmmed.mosa.eg.towmmen.util.ONE_DAY
import com.mohmmed.mosa.eg.towmmen.util.PRODUCT_KEY
import com.mohmmed.mosa.eg.towmmen.util.dateToString
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullProductInfoScreen(navController: NavHostController) {
    val productViewModel:ProductViewModel = hiltViewModel()
    navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Product?>(PRODUCT_KEY)?.let { product ->
            FullProductInfoContent(
                product = product,
                onClickDeleteClick = {
                    productViewModel.deleteProduct(it)
                    navController.popBackStack()
                },
                onClickEditClick = {}

            )
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
                .build(),
                contentDescription = "",
                modifier = Modifier
                    .padding(4.dp)
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.None
            )
            
/*            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = "Product Image",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.None
            )*/

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

            TextWithIcon(
                icon = R.drawable.bag,
                text = String.format(stringResource(R.string.product_name), product.name),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))

            TextWithIcon(
                icon = R.drawable.description,
                text = String.format(stringResource(R.string.description_1), product.description),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))

            TextWithIcon(
                icon = R.drawable.tag,
                text = String.format(
                    stringResource(id = R.string.cost),
                    formatCurrency(product.price)
                ),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))

            TextWithIcon(
                icon = R.drawable.money,
                text = String.format(
                    stringResource(id = R.string.sell_price),
                    formatCurrency(product.price * 1.15)  ,
                    formatCurrency(product.price * 1.2)
                ),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))

            TextWithIcon(
                icon = R.drawable.box,
                text = String.format(
                    stringResource(id = R.string.quantity_2),
                    product.stockQuantity,
                    product.unit),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))

            TextWithIcon(
                icon = R.drawable.calendar_month,
                text = String.format(
                    stringResource(id = R.string.manf_date_1),
                    dateToString(product.manufactureDate, "yyyy/MM/dd")
                ),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))

            TextWithIcon(
                icon = R.drawable.calendar_month,
                text = String.format(
                    stringResource(id = R.string.exp_date_1),
                    dateToString(product.expireDate, "yyyy/MM/dd")
                ),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))

            TextWithIcon(
                icon = R.drawable.calendar_month,
                text =
                    if(product.expireDate.time - Date().time > 0){
                        String.format(
                            stringResource(id = R.string.days_until_expire),
                            ((product.expireDate.time - Date().time) / ONE_DAY)
                        )
                    } else{
                        stringResource(id = R.string.exp_product)
                    },
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))

            TextWithIcon(
                icon = R.drawable.category,
                text = String.format(stringResource(id = R.string.category)),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(bottom = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                product.category.split(",").forEach { category ->
                    if(category.isNotEmpty()){
                        FilterChip(
                            selected = true,
                            onClick = {},
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
    }
}

