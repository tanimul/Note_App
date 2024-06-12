package org.tanimul.notes.ui.fragments.home
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import org.tanimul.notes.data.database.NoteDatabase
import org.tanimul.notes.data.model.NoteModel
import timber.log.Timber
import javax.inject.Inject

class HomeRepository @Inject constructor(private val roomDatabase: NoteDatabase) {

    var showAllNotes: Flow<List<NoteModel>> = roomDatabase.noteDao().showAllNotes()

    suspend fun deleteSingleNote(noteModel: NoteModel) {
        roomDatabase.noteDao().deleteSingleNote(noteModel)
        Timber.d("deleteSingleNote: ")
    }

    suspend fun deleteAllNotes() {
        roomDatabase.noteDao().deleteAllNotes()
        Timber.d("deleteAllNotes: ")
    }
}