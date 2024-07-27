package org.tanimul.notes.ui.fragments.editor.domain.usecase

import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.ui.fragments.editor.domain.repository.UpdateNoteRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(private val updateNoteRepository: UpdateNoteRepository) {
    suspend operator fun invoke(noteModel: NoteModel) {
        updateNoteRepository.updateExistingNote(noteModel)
    }
}