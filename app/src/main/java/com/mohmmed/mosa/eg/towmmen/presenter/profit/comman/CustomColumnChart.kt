package com.mohmmed.mosa.eg.towmmen.presenter.profit.comman

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.ExpansePerMonth
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceProfitByMonth
import com.mohmmed.mosa.eg.towmmen.presenter.profit.rememberMarker
import com.mohmmed.mosa.eg.towmmen.ui.theme.Green40
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.decoration.rememberHorizontalLine
import com.patrykandpatrick.vico.compose.cartesian.fullWidth
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.HorizontalLayout
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.decoration.Decoration
import com.patrykandpatrick.vico.core.cartesian.decoration.HorizontalLine
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.shape.Shape
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun CustomColumnChart(
    modifier: Modifier = Modifier,
    average: Double,
    profitByMonth: List<InvoiceProfitByMonth>

) {
    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(profitByMonth) {
        withContext(Dispatchers.Default) {
            modelProducer.runTransaction {
                columnSeries { series(List(profitByMonth.size) {
                    profitByMonth[it].profit
                }) }
            }
        }
    }
    ColumnChart(modelProducer,
        modifier,
        listOf(
            rememberComposeHorizontalLine(average, Green40, stringResource(R.string.avg))
        )
    ) { x, _, _ ->
        profitByMonth[x.toInt()].month
    }
}


@Composable
fun CustomColumnChart2(
    modifier: Modifier = Modifier,
    average: Double,
    expanseByMonth: List<ExpansePerMonth>

) {
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(expanseByMonth) {
        withContext(Dispatchers.Default) {
            modelProducer.runTransaction {
                columnSeries { series(List(expanseByMonth.size) {
                    expanseByMonth[it].total
                }) }
            }
        }
    }
    ColumnChart(modelProducer,
        modifier,
        listOf(
            rememberComposeHorizontalLine(average, Green40, stringResource(R.string.avg))
        )
    ) { x, _, _ ->
        expanseByMonth[x.toInt()].month
    }
}
@Composable
private fun ColumnChart(modelProducer: CartesianChartModelProducer,
                        modifier: Modifier,
                        decorations: List<Decoration>,
                        cartesianValueFormatter: CartesianValueFormatter
) {
    ProvideVicoTheme(theme = rememberM3VicoTheme()) {

        CartesianChartHost(
            chart =
            rememberCartesianChart(
                rememberColumnCartesianLayer(
                    ColumnCartesianLayer.ColumnProvider.series(
                        rememberLineComponent(
                            color = MaterialTheme.colorScheme.primary,
                            thickness = 12.dp,
                            shape = remember { Shape.rounded(allPercent = 40) },
                        )
                    )
                ),
                startAxis = rememberStartAxis(),
                bottomAxis =
                rememberBottomAxis(
                    //label = TextComponent(color = MaterialTheme.colorScheme.secondary.toArgb()),
                    valueFormatter = cartesianValueFormatter,
                    itemPlacer =
                    remember { HorizontalAxis.ItemPlacer.default(addExtremeLabelPadding = true) },
                ),
                marker = rememberMarker(),
                horizontalLayout = HorizontalLayout.fullWidth(),
                decorations = decorations,
            ),
            modelProducer = modelProducer,
            modifier = modifier,
        )
    }
}


@Composable
private fun rememberComposeHorizontalLine(yValue: Double,
                                          color: Color,
                                          label: String = ""): HorizontalLine {
    return rememberHorizontalLine(
        y = { yValue },
        line = rememberLineComponent(color, 2.dp),
        labelComponent =
        rememberTextComponent(
            margins = Dimensions.of(8.dp),
            padding = Dimensions.of(8.dp, 2.dp,),
            background = rememberShapeComponent(color, Shape.Pill),
        ),
        label = { String.format("%s:%.2f",label, yValue ) }
    )
}

