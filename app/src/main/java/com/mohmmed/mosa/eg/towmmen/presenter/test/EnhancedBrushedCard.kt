package com.mohmmed.mosa.eg.towmmen.presenter.test



import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EnhancedBrushedCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    gradientColors: List<Color>
) {
    var isHovered by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isHovered) 1.05f else 1f)
    val rotationAngle by animateFloatAsState(
        targetValue = if (isHovered) 5f else 0f,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

    Box(
        modifier = Modifier
            .size(300.dp)
            .scale(scale)
            .rotate(rotationAngle)
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.linearGradient(colors = gradientColors)
            )
           /* .hoverable(
                interactionSource = remember { MutableInteractionSource() },
                onHover = { isHovered = it }
            )*/
    ) {
        // Animated background circles
        repeat(3) { index ->
            val circleScale by rememberInfiniteTransition().animateFloat(
                initialValue = 0.6f,
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(index * 500)
                )
            )
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .scale(circleScale)
                    .align(Alignment.Center)
                    .background(
                        Color.White.copy(alpha = 0.1f),
                        shape = CircleShape
                    )
            )
        }

        // Icon
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.2f),
            modifier = Modifier
                .size(220.dp)
                .align(Alignment.Center)
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle,
                fontSize = 20.sp,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun EnhancedBrushedCardPreviw() {

    EnhancedBrushedCard(title = "Like Card",
        subtitle = "Appreciate the moment",
        icon = Icons.Default.ThumbUp,
        gradientColors = listOf(Color(0xFF1B5E20), Color(0xFF4CAF50)))
}

