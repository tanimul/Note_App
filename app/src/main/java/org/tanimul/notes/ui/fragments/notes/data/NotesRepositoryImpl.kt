package org.tanimul.notes.ui.fragments.notes.data

import kotlinx.coroutines.flow.Flow
import org.tanimul.notes.data.database.NoteDatabase
import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.ui.fragments.notes.domain.repository.NotesRepository
import timber.log.Timber
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(private val roomDatabase: NoteDatabase) :
    NotesRepository {
    override suspend fun notes(): Flow<List<NoteModel>> {
        return roomDatabase.noteDao().showNotes()
    }

    override suspend fun deleteNote(noteModel: NoteModel) {
        roomDatabase.noteDao().deleteNote(noteModel)
    }

    override suspend fun deleteNotes() {
        roomDatabase.noteDao().deleteNotes()
    }

}