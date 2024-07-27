package org.tanimul.notes.ui.fragments.notes
import kotlinx.coroutines.flow.Flow
import org.tanimul.notes.data.database.NoteDatabase
import org.tanimul.notes.data.model.NoteModel
import timber.log.Timber
import javax.inject.Inject

class HomeRepository @Inject constructor(private val roomDatabase: NoteDatabase) {

    var showAllNotes: Flow<List<NoteModel>> = roomDatabase.noteDao().showNotes()

    suspend fun deleteSingleNote(noteModel: NoteModel) {
        roomDatabase.noteDao().deleteNote(noteModel)
        Timber.d("deleteSingleNote: ")
    }

    suspend fun deleteAllNotes() {
        roomDatabase.noteDao().deleteNotes()
        Timber.d("deleteAllNotes: ")
    }
}