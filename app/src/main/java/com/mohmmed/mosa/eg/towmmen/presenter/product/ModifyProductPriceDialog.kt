package com.mohmmed.mosa.eg.towmmen.presenter.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.presenter.comman.TextFiledExposedDropdownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyProductPriceDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (Double, String) -> Unit
) {
    val context = LocalContext.current
    var selectedOperation by remember { mutableStateOf("+") }
    val selectedDropdownOption by remember {
        mutableStateOf(listOf(context.getString(R.string.persentage), context.getString(R.string.number))) }
    var selectedOption by remember { mutableStateOf(selectedDropdownOption[0]) }

    var numberValue by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(id = R.string.increase_decrees_price)) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.inc_dec_info))

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .selectableGroup()
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedOperation == "+",
                        onClick = { selectedOperation = "+" },
                        modifier = Modifier.selectable(
                            selected = selectedOperation == "+",
                            onClick = { selectedOperation = "+" },
                            role = Role.RadioButton
                        )
                    )
                    Text(stringResource(id = R.string.increase))
                    RadioButton(
                        selected = selectedOperation == "-",
                        onClick = { selectedOperation = "-" },
                        modifier = Modifier.selectable(
                            selected = selectedOperation == "-",
                            onClick = { selectedOperation = "-" },
                            role = Role.RadioButton
                        )
                    )
                    Text(stringResource(id = R.string.decrees))

                }

                Spacer(modifier = Modifier.height(16.dp))

                TextFiledExposedDropdownMenu(
                    options = selectedDropdownOption,
                    selectedOption = selectedDropdownOption[0],
                    readOnly = true,
                    label = stringResource(id = R.string.option),
                    onValueChangeEvent = {
                        selectedOption = it
                    },
                    leadingIcon = if(selectedOption == stringResource(R.string.persentage))
                        R.drawable.percent_24
                    else R.drawable.quantity
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = numberValue,
                    onValueChange = { numberValue = it },
                    label = { Text(stringResource(R.string.enter_value)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val value = if(selectedOperation == "-")
                            (numberValue.toDoubleOrNull() ?: 0.0) * -1
                    else
                        (numberValue.toDoubleOrNull() ?: 0.0)
                    onConfirm(value, selectedOption) }
            ) {
                Text(stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(id = R.string.dismiss))
            }
        }
    )
}



@Composable
fun ModifyProductPriceDialog2(
    categoryOptions: List<String>,
    onDismissRequest: () -> Unit,
    onConfirm: (Double, String, String) -> Unit
) {
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf(context.getString(R.string.all_products)) }
    var selectedOperation by remember { mutableStateOf("+") }
    val selectedDropdownOption by remember {
        mutableStateOf(listOf(context.getString(R.string.persentage), context.getString(R.string.number))) }
    var selectedOption by remember { mutableStateOf(selectedDropdownOption[0]) }

    var numberValue by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(id = R.string.increase_decrees_price)) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.inc_dec_info))

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .selectableGroup()
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedOperation == "+",
                        onClick = { selectedOperation = "+" },
                        modifier = Modifier.selectable(
                            selected = selectedOperation == "+",
                            onClick = { selectedOperation = "+" },
                            role = Role.RadioButton
                        )
                    )
                    Text(stringResource(id = R.string.increase))
                    RadioButton(
                        selected = selectedOperation == "-",
                        onClick = { selectedOperation = "-" },
                        modifier = Modifier.selectable(
                            selected = selectedOperation == "-",
                            onClick = { selectedOperation = "-" },
                            role = Role.RadioButton
                        )
                    )
                    Text(stringResource(id = R.string.decrees))

                }

                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(16.dp))
                TextFiledExposedDropdownMenu(
                    options = categoryOptions,
                    selectedOption = selectedCategory,
                    readOnly = true,
                    label = stringResource(id = R.string.category),
                    onValueChangeEvent = {
                        selectedCategory = it
                    },
                    leadingIcon = R.drawable.category
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextFiledExposedDropdownMenu(
                    options = selectedDropdownOption,
                    selectedOption = selectedDropdownOption[0],
                    readOnly = true,
                    label = stringResource(id = R.string.option),
                    onValueChangeEvent = {
                        selectedOption = it
                    },
                    leadingIcon = if(selectedOption == stringResource(R.string.persentage))
                        R.drawable.percent_24
                    else R.drawable.quantity
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = numberValue,
                    onValueChange = { numberValue = it },
                    label = { Text(stringResource(R.string.enter_value)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val value = if(selectedOperation == "-")
                        (numberValue.toDoubleOrNull() ?: 0.0) * -1
                    else
                        (numberValue.toDoubleOrNull() ?: 0.0)
                    onConfirm(value, selectedOption, selectedCategory) }
            ) {
                Text(stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(id = R.string.dismiss))
            }
        }
    )
}