package com.mohmmed.mosa.eg.towmmen.presenter.comman

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.ui.theme.BlueShades
import com.mohmmed.mosa.eg.towmmen.ui.theme.CairoFont
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency

@Composable
fun ProductCard(modifier: Modifier = Modifier,
                product: Product,
                onClick: (Product)  -> Unit
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(Brush.linearGradient(BlueShades))
            .clickable { onClick(product) },
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
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
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {

                Text(text = product.name,
                    style = MaterialTheme.typography
                    .bodyLarge
                    .copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.surface,
                    fontFamily = CairoFont
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = String.format(stringResource(id = R.string.cost),
                        formatCurrency(product.price)),
                    fontFamily = CairoFont,
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}




