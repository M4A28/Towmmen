package com.mohmmed.mosa.eg.towmmen.presenter.home.comman


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.MostTopProduct
import com.mohmmed.mosa.eg.towmmen.util.formatCurrency

@Composable
fun TopProductExplainedCard(top: List<MostTopProduct>, total: Double) {

    val otherPercentage by remember {
        mutableIntStateOf((100 - top.sumOf { it.percentage }).toInt())
    }
    val otherTotal by remember {
        mutableStateOf( formatCurrency(total - top.sumOf { it.total }))
    }
    val totalCurrency by remember {
        mutableStateOf( formatCurrency(total))
    }


    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.top_products),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.total_product_profit),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = totalCurrency,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))


            top.fastForEach {
                TopProductExplained(
                    productName = it.productName,
                    amount = formatCurrency(it.total),
                    percentage = it.percentage.toInt()
                )
            }
            TopProductExplained(stringResource(id = R.string.other_product),
                otherTotal,
                otherPercentage
            )
        }
    }
}

@Composable
fun TopProductExplained(productName: String, amount: String, percentage: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = productName,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = amount,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { percentage / 100f },
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = "$percentage%",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

