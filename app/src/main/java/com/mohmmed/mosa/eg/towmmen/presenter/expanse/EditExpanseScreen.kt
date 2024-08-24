package com.mohmmed.mosa.eg.towmmen.presenter.expanse

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Expanse
import com.mohmmed.mosa.eg.towmmen.ui.theme.CairoFont
import com.mohmmed.mosa.eg.towmmen.util.EXPANSE_KEY
import com.mohmmed.mosa.eg.towmmen.util.dateToString
import java.util.Date

@Composable
fun EditExpanseScreen(navController: NavHostController){
    val expanseViewModel: ExpanseViewModel = hiltViewModel()

    navController
        .previousBackStackEntry
        ?.savedStateHandle
        ?.get<Expanse?>(EXPANSE_KEY)?.let { expanse ->

            EditExpanseContent( nacController = navController,
                onEditClick = {
                    expanseViewModel.upsertExpanse(it)
                    navController.popBackStack()
                },
                expanse = expanse
            )
        }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpanseContent(
    expanse: Expanse,
    modifier: Modifier = Modifier,
    nacController: NavHostController,
    onEditClick:(Expanse) -> Unit
){
    var amount by remember { mutableStateOf("${expanse.amount}") }
    var expance by remember { mutableStateOf("${expanse.expanse}") }
    var date by remember { mutableStateOf(expanse.payDate) }
    var showDatePicker by remember { mutableStateOf(false) }
    val payDateState = rememberDatePickerState()


    Column(modifier = modifier
        .fillMaxSize()
        .padding(4.dp)) {

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.expanse_explane),
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = expance,
            onValueChange = { expance = it },
            leadingIcon = {
                Icon(painter = painterResource(id = R.drawable.description),
                    contentDescription = null ,
                    modifier = Modifier
                        .size(20.dp)
                )
            },
            placeholder = { Text(text = stringResource(id = R.string.expanse)) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = amount,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
            onValueChange = { amount = it },
            leadingIcon = {
                Icon(painter = painterResource(id = R.drawable.cash),
                    contentDescription = null ,
                    modifier = Modifier
                        .size(20.dp)
                )
            },
            placeholder = { Text(text = stringResource(id = R.string.expanse_amount)) }
        )

        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = dateToString(date, "yyyy-MM-dd"),
            onValueChange = {},
            leadingIcon = {
                Icon(painter = painterResource(id = R.drawable.calendar_month),
                    contentDescription = null ,
                    modifier = Modifier
                        .clickable { showDatePicker = !showDatePicker }
                        .size(20.dp)
                )
            },
            placeholder = { Text(text = stringResource(id = R.string.date)) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Button(
                onClick = {
                    if(expance.isNotEmpty() && amount.isNotEmpty()){
                        onEditClick(
                            Expanse(
                            expanseId = expanse.expanseId,
                            expanse = expance,
                            payDate = date,
                            amount = amount.toDoubleOrNull() ?: 0.0)
                        )
                    }
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "edit expanse")
                Text(
                    stringResource(id = R.string.edit_expanse),
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.
                    labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    fontFamily = CairoFont,)
            }

            Button(
                onClick = {
                    nacController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                border = ButtonDefaults.outlinedButtonBorder) {
                Text(text = stringResource(id = R.string.cancel),
                    style = MaterialTheme.typography.
                    labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    fontFamily = CairoFont,
                    color = MaterialTheme.colorScheme.primary
                )
            }


        }

    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = { showDatePicker = false }) {
                    Text(text = stringResource(id = R.string.ok),
                        fontFamily = CairoFont,
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        ) {
            DatePicker(state = payDateState)
            date = payDateState.selectedDateMillis?.let { Date(it) } ?: Date()
        }
    }






}
