package com.example.noteapp.interfaces

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.noteapp.model.NoteModel

interface NoteDao {

    @Insert
    suspend fun addSingleNote(noteModel: NoteModel) //Add single note


    @Update
    suspend fun updateExistingNote(noteModel: NoteModel) //update single note


    @Delete
    suspend fun deleteSingleNote(noteModel: NoteModel) //delete single note


    @Query("DELETE FROM Note")
    suspend fun deleteAllNotes() //delete all notes


    @Query("SELECT * FROM Note ORDER BY id ASC")
    fun showAllNotes(): LiveData<List<NoteModel>> //showing all notes


}