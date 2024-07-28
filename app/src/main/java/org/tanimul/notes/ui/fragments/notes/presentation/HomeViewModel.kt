package org.tanimul.notes.ui.fragments.notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.ui.fragments.notes.domain.usecase.DeleteNoteUseCase
import org.tanimul.notes.ui.fragments.notes.domain.usecase.DeleteNotesUseCase
import org.tanimul.notes.ui.fragments.notes.domain.usecase.NotesUseCase
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel @Inject constructor(
    private val notesUseCase: NotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val deleteNotesUseCase: DeleteNotesUseCase
) : ViewModel() {
    init {
        viewModelScope.launch {
            fetchNotes()
        }
    }

    private val _fetchNotes = Channel<Unit>(Channel.CONFLATED)
    val fetchNotes = _fetchNotes.receiveAsFlow().flatMapLatest {
        notesUseCase(Unit)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    private fun fetchNotes() {
        _fetchNotes.trySend(Unit)
    }

    fun deleteNote(note: NoteModel) = viewModelScope.launch(Dispatchers.IO) {
        deleteNoteUseCase(note)
    }

    fun deleteNotes() = viewModelScope.launch(Dispatchers.IO) {
        deleteNotesUseCase()
    }


    private val _uiAction = Channel<NotesUiActions>(Channel.CONFLATED)
    val uiAction = _uiAction.receiveAsFlow()
    fun uiAction(action: NotesUiActions) {
        _uiAction.trySend(action)
    }

}

sealed interface NotesUiActions {
    data object NavigateBack : NotesUiActions
}

