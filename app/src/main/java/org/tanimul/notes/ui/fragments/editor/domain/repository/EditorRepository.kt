package org.tanimul.notes.ui.fragments.editor.domain.repository

import org.tanimul.notes.common.domain.model.NoteModel

interface EditorRepository {

    suspend fun addNote(noteModel: NoteModel)

    suspend fun updateNote(noteModel: NoteModel)

    suspend fun deleteNote(noteModel: NoteModel)

}