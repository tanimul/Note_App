package org.tanimul.notes.ui.fragments.editor.data

import org.tanimul.notes.data.database.NoteDatabase
import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.ui.fragments.editor.domain.repository.UpdateNoteRepository
import timber.log.Timber
import javax.inject.Inject

class UpdateNoteRepositoryImpl @Inject constructor(private val roomDatabase: NoteDatabase) :
    UpdateNoteRepository {
    override suspend fun updateExistingNote(noteModel: NoteModel) {
        roomDatabase.noteDao().updateNote(noteModel)
        Timber.d("updateExistingNote: ")
    }
}