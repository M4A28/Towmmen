package com.mohmmed.mosa.eg.towmmen.presenter.note

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Note
import com.mohmmed.mosa.eg.towmmen.presenter.comman.ConfirmationDialog
import com.mohmmed.mosa.eg.towmmen.presenter.comman.EmptyScreen
import com.mohmmed.mosa.eg.towmmen.presenter.comman.NoteCard
import com.mohmmed.mosa.eg.towmmen.presenter.nafgraph.Route
import com.mohmmed.mosa.eg.towmmen.presenter.navigator.navigateToScreen
import com.mohmmed.mosa.eg.towmmen.util.NOTE_KEY


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteScreen(navController: NavHostController){
    val noteViewModel: NoteViewModel = hiltViewModel()
    val notes by noteViewModel.getAllNote()
        .collectAsState(initial = emptyList())
    var showDeleteDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var note by remember{ mutableStateOf<Note?>(null) }

    NoteContent(
        notes = notes,
        onEditClick = {
            navController.currentBackStackEntry?.savedStateHandle?.set(NOTE_KEY, it)
            navController.navigate(Route.EditNoteScreen.route)
        },
        onDeleteClick = {
            showDeleteDialog = !showDeleteDialog
            note = it
        },
        onFapClick = {
            navigateToScreen(navController, Route.AddNoteScreen.route)
        }
    )
    if(showDeleteDialog){
        ConfirmationDialog(
            title = stringResource(R.string.note_delete_conformation),
            text = stringResource(R.string.note_delete_warring),
            dismissText = stringResource(id = R.string.cancel),
            confirmText = stringResource(id = R.string.delete),
            onConfirm = { note?.let { noteViewModel.deleteNote(it)
                showDeleteDialog = !showDeleteDialog
                Toast.makeText(context,
                    context.getString(R.string.note_delete),
                    Toast.LENGTH_SHORT).show()
            } },
            onDismiss = {
                showDeleteDialog = !showDeleteDialog
            }
        )
    }


}


@OptIn(ExperimentalMaterial3Api::class)
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
        topBar = { TopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(id = R.string.notes))
                }

            })
    },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onFapClick()
                }
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }

        }
    ) {innerPadding ->

        if(notes.isNotEmpty()){
            LazyColumn(
                modifier = modifier.padding(innerPadding)
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