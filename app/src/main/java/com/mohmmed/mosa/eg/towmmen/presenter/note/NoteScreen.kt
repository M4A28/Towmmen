package com.mohmmed.mosa.eg.towmmen.presenter.note

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.domin.module.Note
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.comman.NoteCard

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteScreen(
    onFapClick:() -> Unit
){
    val noteViewModel: NoteViewModel = hiltViewModel()
    val notes by noteViewModel
        .getAllNote()
        .collectAsState(initial = emptyList())

    NoteContent(
        notes = notes,
        onEditClick = {},
        onDeleteClick = {},
        onFapClick = {
            onFapClick()
        }
    )

}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteContent(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    onEditClick: (Note) -> Unit,
    onDeleteClick: (Note) -> Unit,
    onFapClick: () -> Unit =  {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onFapClick()
                },
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }

        }
    ) {
        if(notes.isNotEmpty()){
            LazyColumn(
                modifier = modifier
            ) {
                items(notes.size){
                        note ->
                    NoteCard(
                        note = notes[note],
                        onEditClick = {
                            onEditClick(notes[note])
                        },
                        onDeleteClick = {
                            onDeleteClick(notes[note])
                        }

                    )
                }
            }
        }else{
            EmptyScreen(
                massage = stringResource(R.string.no_notes_yet) ,
                icon = R.drawable.notes,
                alphaAnim = 0.6f
            )
        }
    }

}