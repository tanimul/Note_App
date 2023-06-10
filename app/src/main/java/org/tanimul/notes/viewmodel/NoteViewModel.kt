package org.tanimul.notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tanimul.notes.data.database.NoteDatabase
import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.data.repository.NoteRepository

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    companion object{
        private const val TAG = "NoteViewModel"
    }
    val showAllNotes: LiveData<List<NoteModel>>
    private val noteRepository: NoteRepository

    init {
        val workDao = NoteDatabase.getDatabase(application).noteDao()
        noteRepository = NoteRepository(workDao)
        showAllNotes = noteRepository.showAllNotes
    }

    fun addSingleNote(note: NoteModel) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.addSingleNote(note)
    }

    fun updateExistingNote(note: NoteModel) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.updateExistingNote(note)
    }

    fun deleteSingleNote(note: NoteModel) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteSingleNote(note)
    }

    fun deleteAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteAllNotes()
    }

}