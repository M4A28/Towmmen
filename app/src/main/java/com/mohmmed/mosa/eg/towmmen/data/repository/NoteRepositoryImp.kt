package com.mohmmed.mosa.eg.towmmen.data.repository

import com.mohmmed.mosa.eg.towmmen.data.local.dao.NoteDao
import com.mohmmed.mosa.eg.towmmen.data.module.Note
import com.mohmmed.mosa.eg.towmmen.domin.repostory.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImp @Inject constructor(
    private val noteDao: NoteDao

): NoteRepository {
    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    override fun getAllNote(): Flow<List<Note>> {
       return noteDao.getAllNote()
    }

    override fun getNote(note: String): Flow<List<Note>> {
        return noteDao.getNote(note)
    }

    override suspend fun clearNotesDate() {
        noteDao.clearNotesDate()
    }
}