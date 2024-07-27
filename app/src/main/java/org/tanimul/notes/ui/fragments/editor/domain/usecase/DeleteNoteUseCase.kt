package org.tanimul.notes.ui.fragments.editor.domain.usecase

import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.ui.fragments.editor.domain.repository.DeleteNoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val deleteNoteRepository: DeleteNoteRepository) {
    suspend operator fun invoke(noteModel: NoteModel){
        deleteNoteRepository.deleteSingleNote(noteModel)
    }
}