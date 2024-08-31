package com.mohmmed.mosa.eg.towmmen.presenter.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyProductPriceDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    var selectedOption by remember { mutableStateOf("Option 1") }
    var selectedDropdownOption by remember { mutableStateOf("Dropdown 1") }
    var textFieldValue by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Dialog Title") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("This is the dialog text. Please select an option and fill in the required information.")

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .selectableGroup()
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    RadioButton(
                        selected = selectedOption == "Option 1",
                        onClick = { selectedOption = "Option 1" },
                        modifier = Modifier.selectable(
                            selected = selectedOption == "Option 1",
                            onClick = { selectedOption = "Option 1" },
                            role = Role.RadioButton
                        )
                    )
                    RadioButton(
                        selected = selectedOption == "Option 2",
                        onClick = { selectedOption = "Option 2" },
                        modifier = Modifier.selectable(
                            selected = selectedOption == "Option 2",
                            onClick = { selectedOption = "Option 2" },
                            role = Role.RadioButton
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ExposedDropdownMenuBox(
                        expanded = false,
                        onExpandedChange = { },
                    ) {
                        TextField(
                            value = selectedDropdownOption,
                            onValueChange = { },
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = false,
                            onDismissRequest = { },
                        ) {
                            DropdownMenuItem(
                                text = { Text("Dropdown 1") },
                                onClick = { selectedDropdownOption = "Dropdown 1" }
                            )
                            DropdownMenuItem(
                                text = { Text("Dropdown 2") },
                                onClick = { selectedDropdownOption = "Dropdown 2" }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    TextField(
                        value = textFieldValue,
                        onValueChange = { textFieldValue = it },
                        label = { Text("Enter text") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(selectedOption, selectedDropdownOption, textFieldValue) }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Dismiss")
            }
        }
    )
}