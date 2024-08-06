package com.mohmmed.mosa.eg.towmmen.presenter.comman

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.ui.theme.CairoFont
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency

@Composable
fun ProductCard2(modifier: Modifier = Modifier,
                 product: Product,
                 onClick: (Product)  -> Unit) {

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
                contentScale = ContentScale.Crop
            )

            Text(
                text = product.name,
                fontFamily = CairoFont,

                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = product.description,
                fontFamily = CairoFont,
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
                    fontFamily = CairoFont,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = "${product.stockQuantity}  ${product.unit}",
                fontFamily = CairoFont,
                style = MaterialTheme.typography.labelSmall
            )
        }
    // add column 

    }
/*    Box(
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick(product) }
            .shadow(elevation = 4.dp,
                shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
    ) {
    }*/
}

