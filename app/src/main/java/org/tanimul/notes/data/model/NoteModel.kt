package org.tanimul.notes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "Note")
data class NoteModel(
    val noteTitle: String,
    val noteDetails: String,
    val addedAt: Long,
    val updatedAt: Long,
    val importance: Int
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun getDateFormatted(): String {
        //get current date and time
        val formattedDate = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date())

        return when (SimpleDateFormat(
            "MMMM dd, yyyy", Locale.getDefault()
        ).format(updatedAt)) {
            formattedDate -> SimpleDateFormat(
                "hh:mm a", Locale.getDefault()
            ).format(updatedAt)
            else -> SimpleDateFormat(
                "MMMM dd, yyyy", Locale.getDefault()
            ).format(updatedAt)
        }
    }

}