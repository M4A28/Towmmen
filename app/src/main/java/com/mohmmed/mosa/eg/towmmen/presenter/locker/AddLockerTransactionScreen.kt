package com.mohmmed.mosa.eg.towmmen.presenter.locker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Locker
import com.mohmmed.mosa.eg.towmmen.data.module.TransactionType
import com.mohmmed.mosa.eg.towmmen.presenter.comman.TextFiledExposedDropdownMenu
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddLockerTransactionScreen() {
    val lockerViewModel: LockerViewModel = hiltViewModel()
    AddLockerTransactionContent(onSubmitClick = {
        lockerViewModel.upsertLockerTransaction(it)
    })

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLockerTransactionContent(
    onSubmitClick: (Locker) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var transactionType by remember { mutableStateOf(TransactionType.UNDEFINED.name) }
    var notes by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val dateState = rememberDatePickerState()
    val dateFormatter =  SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    var transActionDate by rememberSaveable { mutableStateOf(Date()) }

    val options = mapOf(
        stringResource(id = R.string.undefined) to  TransactionType.UNDEFINED.name,
        stringResource(id = R.string.add) to  TransactionType.ADD.name,
        stringResource(id = R.string.discount) to TransactionType.DISCOUNT.name ,
        stringResource(id = R.string.sell) to TransactionType.SELL.name,
        stringResource(id = R.string.buy) to TransactionType.BUY.name,
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(id = R.string.enter_transaction))
                    }

                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = amount,
                onValueChange = { amount = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.money),
                        contentDescription = null ,
                        modifier = Modifier
                            .size(20.dp)
                    )
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.transaction_amount))
                }
            )

            Spacer(modifier = Modifier.height(6.dp))


            TextFiledExposedDropdownMenu(
                options = options.keys.toList(),
                selectedOption = options.keys.toList()[0],
                label = stringResource(R.string.transaction_type),
                readOnly = true,
                leadingIcon = R.drawable.payment,
                onValueChangeEvent = { transactionType = options.get(it)?: TransactionType.UNDEFINED.name}
            )

            Spacer(modifier = Modifier.height(6.dp))


            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = dateFormatter.format(transActionDate),
                onValueChange = {  },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.calendar_month),
                        contentDescription = null ,
                        modifier = Modifier
                            .clickable { showDatePicker = true }
                            .size(20.dp)
                    )
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.transaction_date))
                }
            )

            Spacer(modifier = Modifier.height(6.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = notes,
                onValueChange = { notes = it },
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.notes),
                        contentDescription = null ,
                        modifier = Modifier
                            .size(20.dp)
                    )
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.notes))
                }
            )
            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text(stringResource(id = R.string.ok))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text(stringResource(id = R.string.cancel))
                        }
                    }
                ) {
                    DatePicker(
                        state = dateState
                    )
                    transActionDate = dateState.selectedDateMillis?.let { Date(it) } ?: Date()
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val locker = Locker(
                        transActonId = 0,
                        transActionType = transactionType,
                        transActionDate = transActionDate,
                        transActionAmount = formatAmount(value = amount.toDoubleOrNull() ?: 0.0, type = transactionType),
                        transActionNote = notes
                    )
                    onSubmitClick(locker)
                    notes = ""
                    amount = ""
                          },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.submit_transaction))
            }
        }


    }

}

fun formatAmount(type: String, value: Double): Double{
    return when(type){
       TransactionType.DISCOUNT.name,
       TransactionType.BUY.name -> -1 * value
        else -> value
    }
}