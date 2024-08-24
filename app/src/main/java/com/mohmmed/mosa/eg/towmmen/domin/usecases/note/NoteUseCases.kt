package com.mohmmed.mosa.eg.towmmen.domin.usecases.note

data class NoteUseCases(
    val insertNote: InsertNote,
    val deleteNote: DeleteNote,
    val updateNote: UpdateNote,
    val getAllNote: GetAllNote,
    val getNote: GetNote,
    val clearNoteDate: ClearNoteDate
) {
}