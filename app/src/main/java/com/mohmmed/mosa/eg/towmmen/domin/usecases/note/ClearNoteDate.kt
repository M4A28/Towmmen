package com.mohmmed.mosa.eg.towmmen.domin.usecases.note

import com.mohmmed.mosa.eg.towmmen.data.repository.NoteRepositoryImp
import javax.inject.Inject

class ClearNoteDate @Inject constructor(
    private val noteRepositoryImp: NoteRepositoryImp
) {
    suspend  operator fun invoke(){
        noteRepositoryImp.clearNotesDate()

    }
}