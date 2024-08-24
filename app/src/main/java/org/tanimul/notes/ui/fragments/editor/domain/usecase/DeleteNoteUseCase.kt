package org.tanimul.notes.ui.fragments.editor.domain.usecase

import org.tanimul.notes.common.domain.model.NoteModel
import org.tanimul.notes.ui.fragments.editor.domain.repository.EditorRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val editorRepository: EditorRepository) {
    suspend operator fun invoke(noteModel: NoteModel) {
        editorRepository.deleteNote(noteModel)
    }
}