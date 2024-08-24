package com.mohmmed.mosa.eg.towmmen.di

import com.mohmmed.mosa.eg.towmmen.data.local.dao.NoteDao
import com.mohmmed.mosa.eg.towmmen.data.local.db.TowmmenDatabase
import com.mohmmed.mosa.eg.towmmen.data.repository.NoteRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.usecases.note.ClearNoteDate
import com.mohmmed.mosa.eg.towmmen.domin.usecases.note.DeleteNote
import com.mohmmed.mosa.eg.towmmen.domin.usecases.note.GetAllNote
import com.mohmmed.mosa.eg.towmmen.domin.usecases.note.GetNote
import com.mohmmed.mosa.eg.towmmen.domin.usecases.note.InsertNote
import com.mohmmed.mosa.eg.towmmen.domin.usecases.note.NoteUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.note.UpdateNote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NoteModule {
    @Provides
    @Singleton
    fun provideNoteDao(towmmenDatabase: TowmmenDatabase) : NoteDao {
        return towmmenDatabase.noteDao
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepositoryImp {
        return NoteRepositoryImp(noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(noteRepository: NoteRepositoryImp): NoteUseCases {
        return NoteUseCases(
            insertNote = InsertNote(noteRepository),
            deleteNote = DeleteNote(noteRepository),
            updateNote = UpdateNote(noteRepository),
            getAllNote = GetAllNote(noteRepository),
            getNote = GetNote(noteRepository),
            clearNoteDate = ClearNoteDate(noteRepository)

        )
    }
}