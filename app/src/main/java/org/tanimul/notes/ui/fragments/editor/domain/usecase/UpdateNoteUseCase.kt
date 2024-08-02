package org.tanimul.notes.ui.fragments.editor.domain.usecase

import org.tanimul.notes.common.domain.model.NoteModel
import org.tanimul.notes.ui.fragments.editor.domain.repository.EditorRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(private val editorRepository: EditorRepository) {
    suspend operator fun invoke(noteModel: NoteModel) {
        editorRepository.updateNote(noteModel)
    }
}