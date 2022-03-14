package org.primeit.primenotes.data.listner

import org.primeit.primenotes.data.model.NoteModel


interface OnNoteClickListener {
    fun onItemClick(noteModel: NoteModel)
}