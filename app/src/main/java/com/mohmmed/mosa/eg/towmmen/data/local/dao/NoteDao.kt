package com.mohmmed.mosa.eg.towmmen.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mohmmed.mosa.eg.towmmen.data.module.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)


    @Delete
    suspend fun deleteNote(note: Note)
    // ordered

    @Query("SELECT * FROM notes ORDER BY date DESC")
    fun getAllNote(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE note = :note ORDER BY date DESC")
    fun getNote(note: String): Flow<List<Note>>

}