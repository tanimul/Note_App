package org.tanimul.notes.ui.fragments.notes.data

import org.tanimul.notes.data.database.NoteDatabase
import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.ui.fragments.editor.domain.repository.DeleteNoteRepository
import org.tanimul.notes.ui.fragments.notes.domain.repository.DeleteAllNoteRepository
import timber.log.Timber
import javax.inject.Inject

class DeleteAllNoteRepositoryImpl @Inject constructor(private val roomDatabase: NoteDatabase) :
    DeleteAllNoteRepository {
    override suspend fun deleteAllNote() {
        TODO("Not yet implemented")
    }

}