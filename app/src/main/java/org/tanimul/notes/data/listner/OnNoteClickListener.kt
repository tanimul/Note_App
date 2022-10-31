package org.tanimul.notes.data.listner

import org.tanimul.notes.data.model.NoteModel


interface OnNoteClickListener {
    fun onItemClick(noteModel: NoteModel)
}