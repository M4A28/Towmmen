package com.mohmmed.mosa.eg.towmmen.presenter.comman

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.domin.module.Product
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency


@Composable
fun ProductCard2(
    modifier: Modifier = Modifier,
    product: Product,
    onClick: (Product)  -> Unit
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .shadow(4.dp)
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick(product) },
    ){
  /*      Image(
            painter = if(product.imagePath.isNotEmpty()){
                rememberAsyncImagePainter(Uri.parse(product.imagePath))
            }else{
                painterResource(id = R.drawable.image)
                 },
            contentDescription = "Product image",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.None
        )*/

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Uri.parse(product.imagePath))
                .placeholder(R.drawable.image)
                .build(),
            contentDescription = "",
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.None
        )

        Spacer(modifier = Modifier.height(4.dp))

        Column {
            TextWithIcon(
                icon = R.drawable.bag,
                iconColor = Color.Gray,
                text = String.format(stringResource(R.string.product_name), product.name),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(2.dp))

            TextWithIcon(
                icon = R.drawable.tag,
                iconColor = Color.Gray,
                text = String.format(
                    stringResource(id = R.string.cost),
                    formatCurrency(product.price)
                ),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(2.dp))

            TextWithIcon(
                icon = R.drawable.money,
                iconColor = Color.Gray,
                text = String.format(stringResource(id = R.string.sell_price),
                    formatCurrency(product.price * 1.15)  ,
                    formatCurrency(product.price * 1.2)),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(2.dp))

/*            TextWithIcon(
                icon = R.drawable.box,
                iconColor = Color.Gray,
                text = String.format(stringResource(id = R.string.quantity_2),
                    product.stockQuantity,
                    product.unit),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(2.dp))

            TextWithIcon(
                icon = R.drawable.calendar_month,
                iconColor = Color.Gray,
                text = String.format(stringResource(id = R.string.exp_date_1),
                    dateToString(product.expireDate, "yyyy/MM/dd")),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(2.dp))*/

        }



    }
}

