package org.tanimul.notes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.tanimul.notes.data.model.NoteModel

@Database(entities = [NoteModel::class], version = 3, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var instance: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            if (instance == null) {

                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context, NoteDatabase::class.java,
                        "Note"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!

        }
    }

}