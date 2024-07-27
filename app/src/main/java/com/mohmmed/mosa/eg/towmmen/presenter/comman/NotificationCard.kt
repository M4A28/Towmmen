package com.mohmmed.mosa.eg.towmmen.presenter.comman

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.domin.module.Product
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency

@Composable
fun NotificationCard(
    modifier: Modifier = Modifier,
    title: String,
    action: String,
    product: Product,
    indicatorColor: Color,
    onCardClick: (Product) -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.surface
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onCardClick(product)
            }
            .clip(RoundedCornerShape(8.dp))
            .shadow(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Side indicator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(indicatorColor)
            ){

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .fillMaxWidth()
                        .background(backgroundColor)

                ) {

                    TextWithIcon(
                        icon = R.drawable.notifications,
                        iconColor = Color.Gray,
                        text = title,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 16.sp,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextWithIcon(
                        icon = R.drawable.bag,
                        iconColor = Color.Gray,
                        text = String.format(stringResource(R.string.product_name), product.name),
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        fontSize = 12.sp,
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
                        fontSize = 12.sp,
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    TextWithIcon(
                        icon = R.drawable.box,
                        iconColor = Color.Gray,
                        text = String.format(stringResource(id = R.string.quantity_2),
                            product.stockQuantity,
                            product.unit),
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        fontSize = 12.sp,
                    )
                    Spacer(modifier = Modifier.height(2.dp))

                    TextWithIcon(
                        icon = R.drawable.action,
                        iconColor = Color.Gray,
                        text = String.format(stringResource(R.string.recommended_action), action),
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        fontSize = 13.sp,
                    )
                    Spacer(modifier = Modifier.height(2.dp))


                }
            }

        }
    }


}
