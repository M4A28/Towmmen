package com.mohmmed.mosa.eg.towmmen.presenter.comman

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.util.ONE_DAY
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.abs

@Composable
fun NotificationCard(
    modifier: Modifier = Modifier,
    product: Product,
    onCardClick: (Product) -> Unit = {},
) {
    val today = LocalDate.now()
    val daysUntilExpiry = ChronoUnit.DAYS.between(today,
        LocalDate.ofEpochDay(product.expireDate.time / ONE_DAY))
    val (status, statusColor) = when {
        daysUntilExpiry > 0 -> stringResource(R.string.expires_in_days, daysUntilExpiry) to MaterialTheme.colorScheme.primary
        daysUntilExpiry < 0 -> stringResource(R.string.expired_days_ago, abs(daysUntilExpiry)) to MaterialTheme.colorScheme.error
        else -> stringResource(R.string.expires_today) to MaterialTheme.colorScheme.error
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCardClick(product) },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.alert_triangle),
                    contentDescription = "Warning",
                    tint = statusColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.product_alert),
                    style = MaterialTheme.typography.titleLarge,
                    color = statusColor
                )
            }


            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.product_name, product.name),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = status,
                style = MaterialTheme.typography.bodyMedium,
                color = statusColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.suggestions),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            getSuggestions(daysUntilExpiry).forEach { (icon, text) ->
                IconBulletPoint(icon = icon, text = text)
            }
        }
        /*Row(modifier = Modifier.fillMaxWidth()) {
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

        }*/
    }


}

@Composable
fun getSuggestions(daysUntilExpiry: Long): List<Pair<Int, String>> {
    return when {
        daysUntilExpiry > 7 -> listOf(
            R.drawable.check_circle to stringResource(R.string.product_is_still_good_for_use),
            R.drawable.kitchen_pack to stringResource(R.string.store_properly_to_maintain_freshness)
        )
        daysUntilExpiry in 1..7 -> listOf(
            R.drawable.alarm to stringResource(R.string.use_the_product_soon),
            R.drawable.description to stringResource(R.string.plan_meals_to_include_this_product)
        )
        daysUntilExpiry == 0L -> listOf(
            R.drawable.calendar_month to stringResource(R.string.use_the_product_today_if_possible),
            R.drawable.description to stringResource(R.string.check_for_any_signs_of_spoilage_before_use)
        )
        daysUntilExpiry > -7 -> listOf(
            R.drawable.description to stringResource(R.string.check_for_signs_of_spoilage_before_use),
            R.drawable.delete to stringResource(R.string.consider_disposing_if_uncertain_about_safety)
        )
        else -> listOf(
            R.drawable.delete to stringResource(R.string.dispose_of_the_product_safely),
            R.drawable.hand_heart to stringResource(R.string.recycle_the_packaging_if_possible)
        )
    }
}
@Composable
fun IconBulletPoint(icon: Int, text: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            painter =  painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}