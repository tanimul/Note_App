package org.tanimul.notes.ui.fragments.editor.domain.repository

import org.tanimul.notes.data.model.NoteModel

interface UpdateNoteRepository {
    suspend fun updateExistingNote(noteModel: NoteModel)
}