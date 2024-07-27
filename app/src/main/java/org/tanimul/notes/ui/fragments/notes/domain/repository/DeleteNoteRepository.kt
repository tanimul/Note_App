package org.tanimul.notes.ui.fragments.notes.domain.repository

import org.tanimul.notes.data.model.NoteModel

interface DeleteNoteRepository {
    suspend fun deleteSingleNote(noteModel: NoteModel)
}