package org.tanimul.notes.ui.fragments.editor.data

import org.tanimul.notes.data.database.NoteDatabase
import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.ui.fragments.editor.domain.repository.AddNoteRepository
import timber.log.Timber
import javax.inject.Inject

class AddNoteRepositoryImpl @Inject constructor(private val roomDatabase: NoteDatabase) :
    AddNoteRepository {
    override suspend fun addSingleNote(noteModel: NoteModel) {
        roomDatabase.noteDao().addNote(noteModel)
        Timber.d("addSingleNote: ")
    }
}