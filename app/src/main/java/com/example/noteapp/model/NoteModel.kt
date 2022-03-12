package com.example.noteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Note")
data class NoteModel(
    val noteTitle: String,
    val noteDetails: String,
    val noteDate: String,
    val noteTime: String,
    val importance: Int
):Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}