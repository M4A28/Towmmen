package com.mohmmed.mosa.eg.towmmen.presenter.note

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mohmmed.mosa.eg.towmmen.domin.module.Note
import com.mohmmed.mosa.eg.towmmen.presenter.comman.NoteCard

@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    onEditClick: (Note) -> Unit,
    onDeleteClick: (Note) -> Unit
    ) {
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
}