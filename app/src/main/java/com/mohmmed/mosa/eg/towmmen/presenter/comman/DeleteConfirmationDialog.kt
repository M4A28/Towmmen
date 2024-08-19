package com.mohmmed.mosa.eg.towmmen.presenter.comman

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R


@Composable
fun DeleteConfirmationDialog(
    title: String,
    massage: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit

) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(8.dp),
        title = {
            Text(text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            ) },

        text = { Text( text = massage,
            style = MaterialTheme.typography.bodyMedium,
            )},
        confirmButton = {
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = { onConfirm() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {

                Text(stringResource(id = R.string.delete))
            }
        },
        dismissButton = {
            TextButton(
                modifier = Modifier.padding(8.dp),
                onClick = { onDismiss() }
            ) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}