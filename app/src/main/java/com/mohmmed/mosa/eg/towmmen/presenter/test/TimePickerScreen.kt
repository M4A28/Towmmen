package com.mohmmed.mosa.eg.towmmen.presenter.test

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerScreen() {
    var selectedTimeH by remember { mutableStateOf("") }
    var selectedTimeM by remember { mutableStateOf("") }
    var showTimePicker by remember { mutableStateOf(false) }
    var timePickerState = rememberTimePickerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { showTimePicker = true }) {
            Text("Select Time")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selected time: ${selectedTimeH}:${selectedTimeM}",
            style = MaterialTheme.typography.bodyLarge
        )

        if (showTimePicker) {
            DatePickerDialog(
                onDismissRequest = { showTimePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showTimePicker = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showTimePicker = false
                    }) {
                        Text("Cancel")
                    }
                }
            ) {
                TimePicker(
                    state = timePickerState,
                    /*onDateChange = { milliseconds ->
                        selectedDate = LocalDate.ofEpochDay(milliseconds / (24 * 60 * 60 * 1000))
                    }*/
                )
                selectedTimeH = timePickerState.hour.toString()
                selectedTimeM = timePickerState.minute.toString()
            }
        }
    }
}