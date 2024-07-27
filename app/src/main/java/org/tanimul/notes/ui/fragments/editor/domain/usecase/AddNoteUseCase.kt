package org.tanimul.notes.ui.fragments.editor.domain.usecase

import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.ui.fragments.editor.domain.repository.AddNoteRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(private val addNoteRepository: AddNoteRepository) {
    suspend operator fun invoke(noteModel: NoteModel){
        addNoteRepository.addSingleNote(noteModel)
    }
}