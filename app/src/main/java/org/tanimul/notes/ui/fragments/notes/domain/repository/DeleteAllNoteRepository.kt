package org.tanimul.notes.ui.fragments.notes.domain.repository

interface DeleteAllNoteRepository {
    suspend fun deleteAllNote()
}