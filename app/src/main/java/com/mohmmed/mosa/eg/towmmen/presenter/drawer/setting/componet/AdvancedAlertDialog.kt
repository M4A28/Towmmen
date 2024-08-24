package com.mohmmed.mosa.eg.towmmen.presenter.drawer.setting.componet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R

@Composable
fun AdvancedAlertDialog(
    title: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    val conformer = stringResource(id = R.string.delete)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.confirm_deletion)) },
        text = {
            Column {
                Text(title)
                Text(text = stringResource(R.string.please_type_delete_to_confirm),
                    modifier = Modifier.padding(top = 16.dp))
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (inputText.lowercase() == conformer.lowercase()) {
                        onConfirm(inputText)
                    }
                },
                enabled = inputText.lowercase() == conformer.lowercase()
            ) {
                Text(stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )

}

