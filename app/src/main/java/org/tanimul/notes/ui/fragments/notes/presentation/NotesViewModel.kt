package org.tanimul.notes.ui.fragments.notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.tanimul.notes.common.domain.model.NoteModel
import org.tanimul.notes.ui.fragments.notes.domain.usecase.DeleteNoteUseCase
import org.tanimul.notes.ui.fragments.notes.domain.usecase.DeleteNotesUseCase
import org.tanimul.notes.ui.fragments.notes.domain.usecase.NotesUseCase
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModel @Inject constructor(
    private val notesUseCase: NotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val deleteNotesUseCase: DeleteNotesUseCase
) : ViewModel() {

    private val _fetchNotes = Channel<Unit>(Channel.CONFLATED)
    val fetchNotes = _fetchNotes.receiveAsFlow().flatMapLatest {
        notesUseCase(Unit)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    private val _originalNotes = MutableStateFlow<List<NoteModel>?>(null)
    val originalNotes = _originalNotes.asStateFlow()

    private val _displayNotes = MutableStateFlow<List<NoteModel>?>(null)
    val displayNotes = _displayNotes.asStateFlow()

    init {
        viewModelScope.launch {
            fetchNotes()
        }

        viewModelScope.launch {
            fetchNotes.collect {
                Timber.d("fetchNotes: $it")
                it?.let {
                    _originalNotes.value = it
                    _displayNotes.value = it
                }

            }
        }
    }

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
