package com.mohmmed.mosa.eg.towmmen.presenter.comman

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomExposedDropdownMenu(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOption: String,
    label: String,
    readOnly: Boolean = true,
    leadingIcon: Int,
    onValueChangeEvent: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(selectedOption) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        CustomTextFiled(
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            readOnly = readOnly,
            value = selectedValue,
            onValueChange = {
                onValueChangeEvent(it)
            },
            label = label,
            leadingIcon = leadingIcon
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption: String ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onValueChangeEvent(selectionOption)
                        selectedValue = selectionOption
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFiledExposedDropdownMenu(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOption: String,
    label: String,
    readOnly: Boolean = true,
    leadingIcon: Int,
    onValueChangeEvent: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(selectedOption) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            readOnly = readOnly,
            value = selectedValue,
            onValueChange = {
                onValueChangeEvent(it)
            },
            leadingIcon = {
                Icon(painter = painterResource(id =leadingIcon),
                    contentDescription = null ,
                    modifier = Modifier
                        .size(20.dp)
                )
            },
            trailingIcon = {
                Icon(imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null ,
                    modifier = Modifier
                        .size(20.dp)
                )
            },
            placeholder = {
                Text(text = label)
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption: String ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onValueChangeEvent(selectionOption)
                        selectedValue = selectionOption
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }

}




