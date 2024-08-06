package com.mohmmed.mosa.eg.towmmen.presenter.comman

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.ui.theme.BlueShades
import com.mohmmed.mosa.eg.towmmen.ui.theme.CairoFont
import java.util.Date

@Composable
fun CustomerCard(
    modifier: Modifier = Modifier,
    customer: Customer,
    onPhoneClick: (Customer) -> Unit = {},
    onClick: (Customer)  -> Unit
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable{onClick(customer)}
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.linearGradient(BlueShades)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.person),
                contentDescription = "Customer image",
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = customer.name,
                    maxLines = 1,
                    fontFamily = CairoFont,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography
                        .bodyLarge
                        .copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = customer.email,
                    maxLines = 1,
                    fontFamily = CairoFont,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = customer.phone,
                    maxLines = 1,
                    fontFamily = CairoFont,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = { onPhoneClick(customer) }) {
                Icon(Icons.Default.Phone, contentDescription = "Contact")
            }
        }
    }

}

@Preview
@Composable
private fun CustomerPreview() {
    CustomerCard( customer = Customer(
        customerId = 0,
        name = "mohammed mosa",
        email = "44545454",
        phone = "011215233662",
        address = "dfdfdfdfdf",
        registrationDate = Date(),
        lastPurchaseDate = null
    ), onClick = {})
}

