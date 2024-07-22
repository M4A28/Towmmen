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
fun DatePickerScreen() {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var dateState = rememberDatePickerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { showDatePicker = true }) {
            Text("Select Date")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selected Date: ${selectedDate?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: "None"}",
            style = MaterialTheme.typography.bodyLarge
        )

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                    }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = dateState,
                    /*onDateChange = { milliseconds ->
                        selectedDate = LocalDate.ofEpochDay(milliseconds / (24 * 60 * 60 * 1000))
                    }*/
                )
                selectedDate = LocalDate.ofEpochDay((dateState.selectedDateMillis?: 5) / (24 * 60 * 60 * 1000))
            }
        }
    }
}