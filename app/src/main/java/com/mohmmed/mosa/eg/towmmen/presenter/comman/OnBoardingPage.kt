package com.mohmmed.mosa.eg.towmmen.presenter.comman

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.mohmmed.mosa.eg.towmmen.presenter.onbording.Page
import com.mohmmed.mosa.eg.towmmen.presenter.onbording.Pages
import com.mohmmed.mosa.eg.towmmen.ui.theme.CairoFont
import com.mohmmed.mosa.eg.towmmen.ui.theme.MediumPadding1
import com.mohmmed.mosa.eg.towmmen.ui.theme.MediumPadding2


@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page
) {
    Column(
        modifier = modifier
    ){
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        Text(modifier = Modifier.padding(horizontal = MediumPadding2),
            text = stringResource(page.title),
            fontFamily = CairoFont,
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold,
                 fontSize = 32.sp),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(modifier = Modifier.padding(horizontal = MediumPadding2),
            text = stringResource(page.description),
            fontFamily = CairoFont,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PagePreviwe() {
    OnBoardingPage(page = Pages[0])

}