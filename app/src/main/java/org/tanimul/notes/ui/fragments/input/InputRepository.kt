package org.tanimul.notes.ui.fragments.input

import org.tanimul.notes.data.database.NoteDatabase
import org.tanimul.notes.data.model.NoteModel
import timber.log.Timber
import javax.inject.Inject

class InputRepository @Inject constructor(private val roomDatabase: NoteDatabase) {
    suspend fun addSingleNote(noteModel: NoteModel) {
        roomDatabase.noteDao().addSingleNote(noteModel)
        Timber.d("addSingleNote: ")
    }

    suspend fun deleteSingleNote(noteModel: NoteModel) {
        roomDatabase.noteDao().deleteSingleNote(noteModel)
        Timber.d("deleteSingleNote: ")
    }

    suspend fun updateExistingNote(noteModel: NoteModel) {
        roomDatabase.noteDao().updateExistingNote(noteModel)
        Timber.d("updateExistingNote: ")
    }
}