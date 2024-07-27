package org.tanimul.notes.ui.fragments.editor.data

import org.tanimul.notes.data.database.NoteDatabase
import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.ui.fragments.editor.domain.repository.AddNoteRepository
import org.tanimul.notes.ui.fragments.editor.domain.repository.EditorRepository
import timber.log.Timber
import javax.inject.Inject

class EditorRepositoryImpl @Inject constructor(private val roomDatabase: NoteDatabase) :
    EditorRepository {

    override suspend fun addNote(noteModel: NoteModel) {
        roomDatabase.noteDao().addNote(noteModel)
    }

    override suspend fun updateNote(noteModel: NoteModel) {
        roomDatabase.noteDao().updateNote(noteModel)
    }

    override suspend fun deleteNote(noteModel: NoteModel) {
        roomDatabase.noteDao().deleteNote(noteModel)
    }

}