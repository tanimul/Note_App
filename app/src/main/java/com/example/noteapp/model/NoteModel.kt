package com.example.noteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Note")
data class NoteModel(
    val NoteTitle: String,
    val noteDetails: String,
    val noteTime: String,
    val importance: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}