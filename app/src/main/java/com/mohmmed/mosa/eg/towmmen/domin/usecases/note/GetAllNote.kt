package com.mohmmed.mosa.eg.towmmen.domin.usecases.note

import com.mohmmed.mosa.eg.towmmen.data.repository.NoteRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.module.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNote @Inject constructor(
    private val noteRepositoryImp: NoteRepositoryImp
) {
    operator fun invoke(): Flow<List<Note>> {
        return noteRepositoryImp.getAllNote()
    }
}