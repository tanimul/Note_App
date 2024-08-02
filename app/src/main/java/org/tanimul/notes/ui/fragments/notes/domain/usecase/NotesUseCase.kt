package org.tanimul.notes.ui.fragments.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.tanimul.notes.common.domain.model.NoteModel
import org.tanimul.notes.ui.fragments.notes.domain.repository.NotesRepository
import javax.inject.Inject

class NotesUseCase @Inject constructor(private val notesRepository: NotesRepository) {
    suspend operator fun invoke(parameter:Unit): Flow<List<NoteModel>> {
        return notesRepository.notes()
    }
}