package org.tanimul.notes.ui.fragments.editor.data

import org.tanimul.notes.common.domain.model.NoteModel
import org.tanimul.notes.db.NoteDatabase
import org.tanimul.notes.ui.fragments.editor.domain.repository.EditorRepository
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