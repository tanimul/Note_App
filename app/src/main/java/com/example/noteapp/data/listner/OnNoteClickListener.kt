package com.example.noteapp.data.listner

import com.example.noteapp.data.model.NoteModel


interface OnNoteClickListener {
    fun onItemClick(noteModel: NoteModel)
}