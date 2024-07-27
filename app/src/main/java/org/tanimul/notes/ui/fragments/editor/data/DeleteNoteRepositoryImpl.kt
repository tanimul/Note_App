package org.tanimul.notes.ui.fragments.editor.data

import org.tanimul.notes.data.database.NoteDatabase
import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.ui.fragments.editor.domain.repository.DeleteNoteRepository
import timber.log.Timber
import javax.inject.Inject

class DeleteNoteRepositoryImpl @Inject constructor(private val roomDatabase: NoteDatabase) :
    DeleteNoteRepository {
    override suspend fun deleteSingleNote(noteModel: NoteModel) {
        roomDatabase.noteDao().deleteNote(noteModel)
        Timber.d("deleteSingleNote: ")
    }

}