package org.tanimul.notes.ui.fragments.notes.domain.usecase

import org.tanimul.notes.common.domain.model.NoteModel
import org.tanimul.notes.ui.fragments.notes.domain.repository.NotesRepository
import javax.inject.Inject

class DeleteNotesUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(){
        notesRepository.deleteNotes()
    }
}