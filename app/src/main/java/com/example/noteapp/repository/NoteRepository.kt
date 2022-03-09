package com.example.noteapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.noteapp.interfaces.NoteDao
import com.example.noteapp.model.NoteModel

class NoteRepository(private val noteDao: NoteDao) {
    var showAllNotes: LiveData<List<NoteModel>> = noteDao.showAllNotes()
    private val TAG = "NoteRepository"
    suspend fun addSingleNote(noteModel: NoteModel) {
        noteDao.addSingleNote(noteModel)
        Log.d(TAG, "addSingleNote: ")
    }

    suspend fun deleteSingleNote(noteModel: NoteModel) {
        noteDao.deleteSingleNote(noteModel)
        Log.d(TAG, "deleteSingleNote: ")
    }

    suspend fun updateExistingNote(noteModel: NoteModel) {
        noteDao.updateExistingNote(noteModel)
        Log.d(TAG, "updateExistingNote: ")
    }

    suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
        Log.d(TAG, "deleteAllNotes: ")
    }
}