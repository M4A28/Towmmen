package com.mohmmed.mosa.eg.towmmen.presenter.note

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Note
import com.mohmmed.mosa.eg.towmmen.util.NOTE_KEY


@Composable
fun EditNoteScreen(navController: NavHostController){
    val noteViewModel:NoteViewModel = hiltViewModel()
    navController
        .previousBackStackEntry
        ?.savedStateHandle
        ?.get<Note?>(NOTE_KEY)?.let{ note ->
            EditNoteContent(
                note = note,
                onCancel = {
                    navController.popBackStack()
                },
                onEditClick = {editNote ->
                    noteViewModel.updateNote(editNote)
                }
            )
        }
}
@Composable
fun EditNoteContent(
    modifier: Modifier = Modifier,
    note: Note?,
    onCancel: () -> Unit,
    onEditClick: (Note) -> Unit
) {

    var title by remember { mutableStateOf(note?.title?:"") }
    var content by remember { mutableStateOf(note?.note?:"") }
    var titleError by remember { mutableStateOf("") }
    var contentError by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF2F3F3))
            .padding(4.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFAFAFA))
                        .padding(15.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.edit_note),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF16191F)
                    )
                }

                // Content
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = stringResource(R.string.title),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF16191F),
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            title = it
                            titleError = ""
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color(0xFFD5DBDB),
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        isError = titleError.isNotEmpty()
                    )
                    if (titleError.isNotEmpty()) {
                        Text(
                            text = titleError,
                            color = Color(0xFFD13212),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }

                    Text(
                        text = stringResource(R.string.content),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF16191F),
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    OutlinedTextField(
                        value = content,
                        onValueChange = {
                            content = it
                            contentError = ""
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(bottom = 5.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color(0xFFD5DBDB),
                        ),
                        isError = contentError.isNotEmpty()
                    )
                    Text(
                        text = stringResource(R.string._1000_characters, content.length),
                        color = if (content.length > 1000) Color(0xFFD13212) else Color(0xFF687078),
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.End)
                    )
                    if (contentError.isNotEmpty()) {
                        Text(
                            text = contentError,
                            color = Color(0xFFD13212),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }
                }

                // Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFAFAFA))
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { onCancel() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
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

                                val _note = Note(
                                    id = note?.id!!,
                                    title = title,
                                    note = content,
                                    importance = 0
                                )
                                onEditClick(_note)
                                title = ""
                                content = ""


                            }
                        },
                        colors = ButtonDefaults
                            .buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    ) {
                        Text(
                            text = stringResource(R.string.save_edit_note),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
