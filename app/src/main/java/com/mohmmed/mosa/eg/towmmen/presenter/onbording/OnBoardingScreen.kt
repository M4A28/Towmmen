package com.mohmmed.mosa.eg.towmmen.presenter.onbording

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.presenter.comman.OnBoardingPage
import com.mohmmed.mosa.eg.towmmen.presenter.comman.PageIndicator
import com.mohmmed.mosa.eg.towmmen.ui.theme.CairoFont
import com.mohmmed.mosa.eg.towmmen.ui.theme.MediumPadding1
import com.mohmmed.mosa.eg.towmmen.ui.theme.PageIndicatorWidth
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun OnBoardingScreen(
    event: (OnBoardingEvent) -> Unit
) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        val pageState = rememberPagerState(initialPage = 0){
            Pages.size
        }

        val buttonState = remember{
            derivedStateOf {
                when(pageState.currentPage){
                    0 -> listOf("", context.getString(R.string.next))
                    1 -> listOf(context.getString(R.string.back), context.getString(R.string.next))
                    2 -> listOf(context.getString(R.string.back),
                        context.getString(R.string.get_start))
                    else -> listOf("", "")
                }
            }
        }
        HorizontalPager(state = pageState ) { index ->
            OnBoardingPage(page = Pages[index])
            
        }
        
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(MediumPadding1)
            .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            PageIndicator(modifier = Modifier.width(PageIndicatorWidth),
                pageSize = Pages.size,
                selectedPage = pageState.currentPage)

            Row(verticalAlignment = Alignment.CenterVertically){
                val scope = rememberCoroutineScope()
                if(buttonState.value[0].isNotEmpty()){

                    Button(onClick = {
                        scope.launch { pageState.animateScrollToPage(pageState.currentPage -1) }
                    },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        border = ButtonDefaults.outlinedButtonBorder) {
                        Text(text = buttonState.value[0],
                            style = MaterialTheme.typography.
                            labelMedium.copy(fontWeight = FontWeight.SemiBold),
                            fontFamily = CairoFont,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {
                    scope.launch {
                        if(pageState.currentPage == 2){
                            event(OnBoardingEvent.SaveAppEntry)
                        }
                        else{
                            pageState.animateScrollToPage(pageState.currentPage +1)
                        }
                    }
                }) {
                    Text(text = buttonState.value[1],
                        style = MaterialTheme.typography.
                        labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        fontFamily = CairoFont
                    )
                }

            }
        }

    }
}