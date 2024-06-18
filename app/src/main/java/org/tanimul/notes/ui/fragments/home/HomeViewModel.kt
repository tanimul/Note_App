package org.tanimul.notes.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.tanimul.notes.data.model.NoteModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepository: HomeRepository
) : ViewModel() {

    private var _showAllNotes: MutableStateFlow<List<NoteModel>> =
        MutableStateFlow(emptyList())
    val showAllNotes: StateFlow<List<NoteModel>> = _showAllNotes

    init {
        viewModelScope.launch {
            noteRepository.showAllNotes.collect {
                _showAllNotes.value = it
            }

        }
    }

    fun deleteSingleNote(note: NoteModel) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteSingleNote(note)
    }

    fun deleteAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteAllNotes()
    }

}