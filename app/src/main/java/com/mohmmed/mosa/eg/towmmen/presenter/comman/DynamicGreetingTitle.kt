package com.mohmmed.mosa.eg.towmmen.presenter.comman

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.mohmmed.mosa.eg.towmmen.R
import java.time.LocalDateTime

data class DynamicGreetingCheckData(
    val dateTime: LocalDateTime
)

// todo add ramdan , eid
enum class DynamicGreetings(
    val content: Int,
    val check: (dynamicGreeting: DynamicGreetingCheckData) -> Boolean
) {
    MORNING(R.string.greeting_good_morning, {
        it.dateTime.hour in 5..10
    }),
    MIDDAY(R.string.greeting_noon, {
        it.dateTime.hour in 11..17
    }),
    EVENING(R.string.greeting_good_evening, {
        it.dateTime.hour in 18..21
    }),
    NIGHT(R.string.greeting_good_night, {
        (it.dateTime.hour in 22..24) || (it.dateTime.hour in 0..4)
    })

}

@Composable
fun DynamicGreetingTitle() {
    val context = LocalContext.current
    var greetingContent by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val data = DynamicGreetingCheckData(
            dateTime = LocalDateTime.now()
        )

        for(entry in DynamicGreetings.entries) {
            if(!entry.check(data)) continue
            greetingContent = context.getString(entry.content)
            break
        }
    }

    Text(text = greetingContent)
}