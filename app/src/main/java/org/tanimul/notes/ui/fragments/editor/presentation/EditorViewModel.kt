package org.tanimul.notes.ui.fragments.editor.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.ui.fragments.editor.domain.usecase.AddNoteUseCase
import org.tanimul.notes.ui.fragments.editor.domain.usecase.DeleteNoteUseCase
import org.tanimul.notes.ui.fragments.editor.domain.usecase.UpdateNoteUseCase
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
) : ViewModel() {

    private val _uiAction = Channel<InputUiActions>(Channel.CONFLATED)
    val uiAction = _uiAction.receiveAsFlow()

    fun uiActions(actions: InputUiActions) {
        _uiAction.trySend(actions)
    }

    fun addNote(noteModel: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            addNoteUseCase(noteModel)
        }
    }

    fun deleteNote(noteModel: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNoteUseCase(noteModel)
        }
    }

    fun updateNote(noteModel: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            updateNoteUseCase(noteModel)
        }
    }
}

sealed interface InputUiActions {
    data object NavigateBack : InputUiActions
    data object AddNote : InputUiActions
    data object DeleteNote : InputUiActions
    data object UpdateNote : InputUiActions
}