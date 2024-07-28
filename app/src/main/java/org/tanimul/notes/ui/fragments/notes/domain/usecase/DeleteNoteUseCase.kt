package org.tanimul.notes.ui.fragments.notes.domain.usecase

import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.ui.fragments.notes.domain.repository.NotesRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(noteModel: NoteModel){
        notesRepository.deleteNote(noteModel)
    }
}