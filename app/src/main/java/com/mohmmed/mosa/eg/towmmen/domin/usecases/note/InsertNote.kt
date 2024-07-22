package com.mohmmed.mosa.eg.towmmen.domin.usecases.note

import com.mohmmed.mosa.eg.towmmen.data.repository.NoteRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.module.Note
import javax.inject.Inject

class InsertNote @Inject constructor(
    private val noteRepositoryImp: NoteRepositoryImp
) {
    suspend  operator fun invoke(note: Note){
        noteRepositoryImp.insertNote(note)

    }
}