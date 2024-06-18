package org.tanimul.notes.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale

@BindingAdapter("app:dateTime")
fun setDateTime(textView: TextView, timestamp: Long?) {
    Timber.d("afwerfaw $timestamp")
    val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy hh:mm a", Locale.getDefault())
    textView.text = dateFormat.format(timestamp ?: System.currentTimeMillis())
}
