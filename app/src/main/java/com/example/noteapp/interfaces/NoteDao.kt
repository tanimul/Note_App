package com.example.noteapp.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.noteapp.model.NoteModel

@Dao
interface NoteDao {

    @Insert
    suspend fun addSingleNote(noteModel: NoteModel) //Add single note


    @Update
    suspend fun updateExistingNote(noteModel: NoteModel) //update single note


    @Delete
    suspend fun deleteSingleNote(noteModel: NoteModel) //delete single note


    @Query("DELETE FROM Note")
    suspend fun deleteAllNotes() //delete all notes


    @Query("SELECT * FROM Note ORDER BY id DESC")
    fun showAllNotes(): LiveData<List<NoteModel>> //showing all notes


}