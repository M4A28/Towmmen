package com.mohmmed.mosa.eg.towmmen.presenter.comman

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.ui.theme.CairoFont


@Composable
fun DeleteConfirmationDialog(
    title: String,
    massage: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit

) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.surface,
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(8.dp),

        title = {
            Text(text = title,
                fontFamily = CairoFont,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ) },

        text = { Text( text = massage,
            fontFamily = CairoFont,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
            )},
        confirmButton = {
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = { onConfirm() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.surface)
            ) {

                Text(stringResource(id = R.string.delete))
            }
        },
        dismissButton = {
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                border = ButtonDefaults.outlinedButtonBorder) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}