package com.mohmmed.mosa.eg.towmmen.presenter.navigator.componet

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R

@Composable
fun NotificationBadge(
    onClick: () -> Unit,
    notifySize: Int
) {
    Box(modifier = Modifier.padding(12.dp)) {
        IconButton(onClick = { onClick() }) {
            Icon(

                painter = painterResource(id = R.drawable.notifications),
                contentDescription = "NotificationIcon"
            )
        }
        if(notifySize > 0){

            Badge(
                modifier = Modifier
                    .border(1.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = CircleShape)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
            ) {
                Text(text ="$notifySize")
            }
        }
    }
}