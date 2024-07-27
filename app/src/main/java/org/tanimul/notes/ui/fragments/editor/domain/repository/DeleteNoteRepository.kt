package org.tanimul.notes.ui.fragments.editor.domain.repository

import org.tanimul.notes.data.model.NoteModel

interface DeleteNoteRepository {
    suspend fun deleteSingleNote(noteModel: NoteModel)
}