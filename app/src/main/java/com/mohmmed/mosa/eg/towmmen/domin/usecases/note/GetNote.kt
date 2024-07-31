package com.mohmmed.mosa.eg.towmmen.domin.usecases.note

import com.mohmmed.mosa.eg.towmmen.data.repository.NoteRepositoryImp
import com.mohmmed.mosa.eg.towmmen.data.module.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNote @Inject constructor(
    private val noteRepositoryImp: NoteRepositoryImp
) {
    operator fun invoke(note: String): Flow<List<Note>> {
        return noteRepositoryImp.getNote(note)
    }
}