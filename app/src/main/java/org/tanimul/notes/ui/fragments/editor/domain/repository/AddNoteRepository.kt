package org.tanimul.notes.ui.fragments.editor.domain.repository

import org.tanimul.notes.data.model.NoteModel

interface AddNoteRepository {
    suspend fun addSingleNote(noteModel: NoteModel)
}