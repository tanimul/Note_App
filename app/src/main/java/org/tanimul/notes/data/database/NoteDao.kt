package org.tanimul.notes.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.tanimul.notes.data.model.NoteModel

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSingleNote(noteModel: NoteModel) //Add single note

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateExistingNote(noteModel: NoteModel) //update single note

    @Delete
    suspend fun deleteSingleNote(noteModel: NoteModel) //delete single note

    @Query("DELETE FROM Note")
    suspend fun deleteAllNotes() //delete all notes

    @Query("SELECT * FROM Note ORDER BY id DESC")
    fun showAllNotes(): Flow<List<NoteModel>> //showing all notes


}