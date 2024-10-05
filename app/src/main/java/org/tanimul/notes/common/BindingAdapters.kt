package org.tanimul.notes.common

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import org.tanimul.notes.R
import java.text.SimpleDateFormat
import java.util.Locale

@BindingAdapter("app:dateTime")
fun setDateTime(textView: TextView, timestamp: Long?) {
    val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy hh:mm a", Locale.getDefault())
    textView.text = dateFormat.format(timestamp ?: System.currentTimeMillis())
}

@BindingAdapter("importanceBackground")
fun setImportanceBackground(view: View, importance: Int) {
    val backgroundColors = listOf(
        R.color.colorNote1,
        R.color.colorNote2,
        R.color.colorNote3
    )

    view.setBackgroundResource(backgroundColors[importance])
}

@BindingAdapter("importanceCardBackground")
fun setImportanceCardBackground(cardView: CardView, importance: Int) {
    val context = cardView.context
    val cardBackgroundColors = listOf(
        R.color.colorBackground1,
        R.color.colorBackground2,
        R.color.colorBackground3
    )

    cardView.setCardBackgroundColor(
        ContextCompat.getColor(
            context,
            cardBackgroundColors[importance]
        )
    )
}




