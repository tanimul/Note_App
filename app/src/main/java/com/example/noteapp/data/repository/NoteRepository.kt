package com.example.noteapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.noteapp.data.database.NoteDao
import com.example.noteapp.data.model.NoteModel

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