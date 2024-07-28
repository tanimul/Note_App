package org.tanimul.notes.ui.fragments.editor.domain.usecase

import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.ui.fragments.editor.domain.repository.EditorRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(private val editorRepository: EditorRepository) {
    suspend operator fun invoke(noteModel: NoteModel){
        editorRepository.addNote(noteModel)
    }
}