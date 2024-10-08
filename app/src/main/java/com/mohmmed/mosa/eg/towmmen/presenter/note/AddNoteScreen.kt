package com.mohmmed.mosa.eg.towmmen.presenter.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    modifier: Modifier = Modifier,
) {

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf("") }
    var contentError by remember { mutableStateOf("") }
    val noteViewModel: NoteViewModel = hiltViewModel()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.insert_note),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                // Content
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = stringResource(R.string.title),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    TextField(
                        value = title,
                        onValueChange = {
                            title = it
                            titleError = ""
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        isError = titleError.isNotEmpty()
                    )
                    if (titleError.isNotEmpty()) {
                        Text(
                            text = titleError,
                            color =  MaterialTheme.colorScheme.error,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }

                    Text(
                        text = stringResource(R.string.content),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    TextField(
                        value = content,
                        onValueChange = {
                            content = it
                            contentError = ""
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(bottom = 5.dp),
                        isError = contentError.isNotEmpty()
                    )
                    Text(
                        text = "${content.length} / 1000 characters",
                        color = if (content.length > 1000)  MaterialTheme.colorScheme.error
                        else  MaterialTheme.colorScheme.primary,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.End)
                    )
                    if (contentError.isNotEmpty()) {
                        Text(
                            text = contentError,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }
                }

                // Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { /* Handle cancel logic */ },
                        colors = ButtonDefaults.buttonColors(containerColor =  MaterialTheme.colorScheme.surface),
                        border = ButtonDefaults.outlinedButtonBorder,
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = Color(0xFF16191F)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            titleError = if (title.isBlank()) "Title is required" else ""
                            contentError = when {
                                content.isBlank() -> "Content is required"
                                content.length > 1000 -> "Content must not exceed 1000 characters"
                                else -> ""
                            }
                            if (titleError.isEmpty() && contentError.isEmpty()) {
                                // Handle successful submission

                                val note = Note(
                                    title = title,
                                    note = content,
                                    importance = 0
                                )
                                noteViewModel.addtNote(note)
                                title = ""
                                content = ""


                            }
                        },
                        colors = ButtonDefaults
                            .buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    ) {
                        Text(
                            text = stringResource(R.string.insert_note),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
