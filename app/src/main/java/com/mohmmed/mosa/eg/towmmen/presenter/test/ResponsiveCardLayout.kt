package com.mohmmed.mosa.eg.towmmen.presenter.test



import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

data class CardData(
    val title: String,
    val subtitle: String,
    val description: String,
    val icon: ImageVector,
    val gradientColors: List<Color>
)

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ResponsiveCardLayout() {
    val configuration = LocalConfiguration.current
    val cardDataList = remember {
        listOf(
            CardData(
                "Star Card",
                "Reach for the stars",
                "Embark on a journey of cosmic discovery and stellar achievements.",
                Icons.Default.Star,
                listOf(Color(0xFF1A237E), Color(0xFF303F9F), Color(0xFF3F51B5))
            ),
            CardData(
                "Love Card",
                "Spread the love",
                "Cultivate compassion and kindness in every interaction.",
                Icons.Default.Favorite,
                listOf(Color(0xFFB71C1C), Color(0xFFE57373), Color(0xFFFF8A80))
            ),
            CardData(
                "Like Card",
                "Appreciate the moment",
                "Find joy in the little things and celebrate small victories.",
                Icons.Default.ThumbUp,
                listOf(Color(0xFF1B5E20), Color(0xFF4CAF50), Color(0xFF81C784))
            )
        )
    }

    if (configuration.screenWidthDp >= 600) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp)
        ) {
            items(cardDataList) { cardData ->
                SuperEnhancedBrushedCard(cardData)
            }
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp)
        ) {
            items(cardDataList) { cardData ->
                SuperEnhancedBrushedCard(cardData)
            }
        }
    }
}

@Composable
fun SuperEnhancedBrushedCard(cardData: CardData) {
    var isExpanded by remember { mutableStateOf(false) }
    var isHovered by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isHovered) 1.05f else 1f)
    val rotationAngle by animateFloatAsState(
        targetValue = if (isHovered) 5f else 0f,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )
    val cardHeight by animateIntAsState(if (isExpanded) 400 else 300)

    Box(
        modifier = Modifier
            .width(300.dp)
            .height(cardHeight.dp)
            .scale(scale)
            .rotate(rotationAngle)
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.linearGradient(colors = cardData.gradientColors))
            .border(2.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(24.dp))
            .clickable { isExpanded = !isExpanded }
     /*       .hoverable(
                interactionSource = remember { MutableInteractionSource() },
                onHover = { isHovered = it }
            )*/
    ) {
        // Animated particles
        repeat(20) {
            val particleSize = remember { Random.nextInt(5, 15).dp }
            val xOffset = remember { Random.nextInt(-150, 150).dp }
            val yOffset = remember { Random.nextInt(-200, 200).dp }

            val animatedY by rememberInfiniteTransition().animateFloat(
                initialValue = yOffset.value,
                targetValue = yOffset.value - 200f,
                animationSpec = infiniteRepeatable(
                    animation = tween(Random.nextInt(3000, 5000), easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )

            Box(
                modifier = Modifier
                    .size(particleSize)
                    .offset(x = xOffset, y = animatedY.dp)
                    .background(Color.White.copy(alpha = 0.5f), CircleShape)
            )
        }

        // Icon
        Icon(
            imageVector = cardData.icon,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.2f),
            modifier = Modifier
                .size(220.dp)
                .align(Alignment.Center)
                .rotate(rotationAngle)
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
                text = cardData.title,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = cardData.subtitle,
                fontSize = 20.sp,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
            if (isExpanded) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = cardData.description,
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Handle action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Learn More", color = cardData.gradientColors[0])
                }
            }
        }
    }
}