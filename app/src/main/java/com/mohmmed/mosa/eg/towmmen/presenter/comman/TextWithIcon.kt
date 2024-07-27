package com.mohmmed.mosa.eg.towmmen.presenter.comman

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.ui.theme.CairoFont

@Composable
fun TextWithIcon(
    modifier: Modifier = Modifier,
    icon: Int,
    iconColor: Color = MaterialTheme.colorScheme.primary,
    text: String,
    fontWeight: FontWeight? = null,
    color: Color = Color.Black,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontFamily: FontFamily? = CairoFont,
    style: TextStyle = LocalTextStyle.current

) {

    Row(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = iconColor
        )

        Spacer(modifier = Modifier.width(3.dp))

        Text(
            text = text,
            style = style,
            color = color,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            fontSize = fontSize
        )
    }

}