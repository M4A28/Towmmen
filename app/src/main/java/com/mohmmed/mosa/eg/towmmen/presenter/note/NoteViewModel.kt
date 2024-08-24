package com.mohmmed.mosa.eg.towmmen.presenter.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.data.module.Note
import com.mohmmed.mosa.eg.towmmen.domin.usecases.note.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    fun getAllNote(): Flow<List<Note>> {
        return noteUseCases.getAllNote()
    }

    fun searchNote(note: String): Flow<List<Note>> {
        return noteUseCases.getNote(note)
    }

    fun addtNote(note: Note){
        viewModelScope.launch {
            noteUseCases.insertNote(note)
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            noteUseCases.deleteNote(note)
        }
    }
    fun updateNote(note: Note){
        viewModelScope.launch {
            noteUseCases.updateNote(note)
        }
    }

    fun clearNoteDate(){
        viewModelScope.launch(Dispatchers.IO) {
            noteUseCases.clearNoteDate()
        }
    }

}

