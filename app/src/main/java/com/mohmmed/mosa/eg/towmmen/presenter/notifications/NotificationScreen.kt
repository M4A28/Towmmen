package com.mohmmed.mosa.eg.towmmen.presenter.notifications

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mohmmed.mosa.eg.towmmen.domin.module.Product
import com.mohmmed.mosa.eg.towmmen.presenter.comman.NotificationCard
import com.mohmmed.mosa.eg.towmmen.ui.theme.Green40
import com.mohmmed.mosa.eg.towmmen.ui.theme.Red40
import com.mohmmed.mosa.eg.towmmen.ui.theme.Red80
import com.mohmmed.mosa.eg.towmmen.ui.theme.Yellow40
import com.mohmmed.mosa.eg.towmmen.util.ONE_DAY
import com.mohmmed.mosa.eg.towmmen.util.ONE_WEEK
import com.mohmmed.mosa.eg.towmmen.util.TOW_WEEKS
import java.util.Date

@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    expProducts: List<Product>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(expProducts.size){product ->
            NotificationCard(
                title = title(expProducts[product].expireDate),
                action = action(expProducts[product].expireDate),
                product = expProducts[product],
                indicatorColor = Red40 /*indicatorColor(expProducts[product].expireDate)*/,
            )
        }

    }

}


fun title(date: Date): String{
    val currentDate = Date()
    val value = date.time - currentDate.time
    val day = value / ONE_DAY

    return when {
        value <= ONE_DAY -> "تحذير: منتج على وشك الانتهاء خلال يوم"
        value <= ONE_WEEK ->  "يوم" + "$day" + "منتج شارف على الانتهاء خلال"
        value <= TOW_WEEKS -> " يوم $day تحذير: منتج ينتهي خلال "
        value <= 0 -> "تحذير منتج منتهي الصلاحية"
        else -> "منتج شارف على الانتهاء"
    }
}

fun action(date: Date): String{
    val currentDate = Date()
    val value = date.time - currentDate.time
    return when {
        value <= ONE_DAY -> "التخلص من المنتح"
        value <= ONE_WEEK -> "التبرع للجمعيات الخيرية و المحتاجين او الاستعمال الشخصي"
        value <= TOW_WEEKS -> "التبرع للجمعيات الخيرية و المحتاجين"
        else -> "البيع في اسرع وقت ممكن"
    }
}

fun indicatorColor(date: Date): Color {
    val currentDate = Date()
    val value = date.time - currentDate.time
    return when {
        value <= ONE_DAY -> Red40
        value <= ONE_WEEK -> Yellow40
        value <= TOW_WEEKS -> Green40
        else -> Red80
    }
}


