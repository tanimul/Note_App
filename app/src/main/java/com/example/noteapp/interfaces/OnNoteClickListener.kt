package com.example.noteapp.interfaces

import android.app.TaskInfo
import com.example.noteapp.model.NoteModel


interface OnNoteClickListener {
    fun onItemClick(noteModel: NoteModel)
}