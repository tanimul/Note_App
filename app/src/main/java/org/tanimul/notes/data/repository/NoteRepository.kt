package org.tanimul.notes.data.repository
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import org.tanimul.notes.data.database.NoteDatabase
import org.tanimul.notes.data.model.NoteModel
import timber.log.Timber
import javax.inject.Inject

class NoteRepository @Inject constructor(private val roomDatabase: NoteDatabase) {

    var showAllNotes: Flow<List<NoteModel>> = roomDatabase.noteDao().showAllNotes()

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

    suspend fun deleteAllNotes() {
        roomDatabase.noteDao().deleteAllNotes()
        Timber.d("deleteAllNotes: ")
    }
}