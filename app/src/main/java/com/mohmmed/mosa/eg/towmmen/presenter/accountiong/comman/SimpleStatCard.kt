package com.mohmmed.mosa.eg.towmmen.presenter.accountiong.comman

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun SimpleStatCard(title: String,
                   modifier: Modifier = Modifier,
                   value: String
) {
    ElevatedCard(
        modifier = modifier.animateContentSize(),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Column(modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(16.dp))
        ) {
            Text(title, style = MaterialTheme.typography.titleSmall)
            Text(value, style = MaterialTheme.typography.headlineMedium)
        }
    }
}
