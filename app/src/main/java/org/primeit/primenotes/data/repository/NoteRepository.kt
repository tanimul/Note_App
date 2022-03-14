package org.primeit.primenotes.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import org.primeit.primenotes.data.database.NoteDao
import org.primeit.primenotes.data.model.NoteModel

class NoteRepository(private val noteDao: NoteDao) {
    private val TAG= "NoteRepository"
    var showAllNotes: LiveData<List<NoteModel>> = noteDao.showAllNotes()

    suspend fun addSingleNote(noteModel: NoteModel) {
        noteDao.addSingleNote(noteModel)
        Log.d(TAG, "addSingleNote: ")
    }

    suspend fun deleteSingleNote(noteModel: NoteModel) {
        noteDao.deleteSingleNote(noteModel)
        Log.d(TAG, "deleteSingleNote: ")
    }

    suspend fun updateExistingNote(noteModel: NoteModel) {
        noteDao.
        updateExistingNote(noteModel)
        Log.d(TAG, "updateExistingNote: ")
    }

    suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
        Log.d(TAG, "deleteAllNotes: ")
    }
}