package com.mohmmed.mosa.eg.towmmen.presenter.comman

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency

@Composable
fun ProductCard2(modifier: Modifier = Modifier,
                 product: Product,
                 onClick: (Product)  -> Unit) {
    val context = LocalContext.current
    val persistedUri =Uri.parse(product.imagePath)

    Card(modifier = modifier
        .padding(8.dp)
        .fillMaxWidth()
        .shadow(4.dp, RoundedCornerShape(16.dp))
        .clickable { onClick(product) },
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)

    ) {
        Column(
            modifier = Modifier.padding(top = 2.dp, bottom = 6.dp, end = 6.dp, start = 6.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(persistedUri)
                    .placeholder(R.drawable.image)
                    .crossfade(true)
                    .error(R.drawable.image)
                    .diskCacheKey(product.imagePath)
                    .memoryCacheKey(product.imagePath)
                    .build(),
                contentDescription = "",
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = product.description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatCurrency(product.price),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = if(product.stockQuantity <= 0) stringResource(id = R.string.out_of_stock) else "${product.stockQuantity}  ${product.unit}",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
// todo remove this or fix it  iamge not load after relaunch
@Composable
fun persistUriPermission(context: Context, uri: Uri): Uri {
  /*  val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION
    context.contentResolver.takePersistableUriPermission(uri, takeFlags)*/
    return uri
}