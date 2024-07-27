package org.tanimul.notes.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.tanimul.notes.data.model.NoteModel

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(noteModel: NoteModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(noteModel: NoteModel)

    @Delete
    suspend fun deleteNote(noteModel: NoteModel)

    @Query("DELETE FROM Note")
    suspend fun deleteNotes()

    @Query("SELECT * FROM Note ORDER BY id DESC")
    fun showNotes(): Flow<List<NoteModel>>


}