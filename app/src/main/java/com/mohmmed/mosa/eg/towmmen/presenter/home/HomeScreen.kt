package com.mohmmed.mosa.eg.towmmen.presenter.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.presenter.comman.SimpleInfo
import com.mohmmed.mosa.eg.towmmen.presenter.comman.SingleInfoCard
import com.mohmmed.mosa.eg.towmmen.presenter.customer.CustomerViewModel
import com.mohmmed.mosa.eg.towmmen.presenter.product.ProductViewModel
import com.mohmmed.mosa.eg.towmmen.ui.theme.BlueShades
import com.mohmmed.mosa.eg.towmmen.ui.theme.OrangeShades
import com.mohmmed.mosa.eg.towmmen.ui.theme.RedShades

@Composable
fun HomeScreen() {
    val productViewModel: ProductViewModel = hiltViewModel()
    val productsCost by productViewModel
        .getAllProductCost().collectAsState(initial = 0.0)
    val productsCount by productViewModel.getProductsCount().collectAsState(initial = 0)
    val customerViewModel: CustomerViewModel = hiltViewModel()
    val customerCount by customerViewModel
        .getCustomerCount().collectAsState(initial = 0)

    val infos = listOf(
        SimpleInfo(
            title = stringResource(R.string.total_product),
            subtitle = "$productsCount",
            icon = R.drawable.bag,
            gradientColors = OrangeShades

        ) ,
        SimpleInfo(
            title = stringResource(R.string.total_customer),
            subtitle = "$customerCount",
            icon = R.drawable.person,
            gradientColors = RedShades

        ),
        SimpleInfo(
            title = stringResource(R.string.total_product_cost),
            subtitle = String.format("%.2f", productsCost),
            icon = R.drawable.money,
            gradientColors = BlueShades

        )
    )
    HomeContent(infoes = infos)
}

@Composable
fun HomeContent(
    infoes: List<SimpleInfo>
) {
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        item{
            LazyRow {
                items(infoes){
                    Box(
                        modifier = Modifier.padding(end = 8.dp, start = 8.dp)
                    ){

                        SingleInfoCard(
                            title = it.title,
                            subtitle = it.subtitle,
                            icon = it.icon,
                            gradientColors = it.gradientColors,
                            onCardClick = {})
                    }
                }
            }
        }

   /*     item{
            val pointsData: List<Point> =
                listOf(Point(0f, 40f), Point(1f, 90f),
                    Point(2f, 0f), Point(3f, 60f), Point(4f, 10f))

            val xAxisData = AxisData.Builder()
                .axisStepSize(100.dp)
                .backgroundColor(Color.Blue)
                .steps(pointsData.size - 1)
                .labelData { i -> i.toString() }
                .labelAndAxisLinePadding(15.dp)
                .build()

            val steps = 5

            val yAxisData = AxisData.Builder()
                .steps(steps)
                .backgroundColor(Color.Red)
                .labelAndAxisLinePadding(20.dp)
                .labelData { i ->
                    val yScale = 100 / steps
                    (i * yScale).toString()
                }.build()


            val lineChartData = LineChartData(
                linePlotData = LinePlotData(
                    lines = listOf(
                        Line(
                            dataPoints = pointsData,
                            LineStyle(),
                            IntersectionPoint(),
                            SelectionHighlightPoint(),
                            ShadowUnderLine(),
                            SelectionHighlightPopUp()
                        )
                    ),
                ),
                xAxisData = xAxisData,
                yAxisData = yAxisData,
                gridLines = GridLines(),
                backgroundColor = Color.White
            )

            LazyRow {
                items(infoes){
                    Box(
                        modifier = Modifier.padding(end = 8.dp, start = 8.dp)
                    ){

                        LineChart(
                            modifier = Modifier
                                .width(300.dp)
                                .height(200.dp),
                            lineChartData = lineChartData
                        )
                    }
                }
            }
        }*/


    }
}
