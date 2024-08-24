package org.tanimul.notes.ui.fragments.notes.domain.repository

import kotlinx.coroutines.flow.Flow
import org.tanimul.notes.common.domain.model.NoteModel

interface NotesRepository {

    suspend fun notes(): Flow<List<NoteModel>>

    suspend fun deleteNote(noteModel: NoteModel)

    suspend fun deleteNotes()

}