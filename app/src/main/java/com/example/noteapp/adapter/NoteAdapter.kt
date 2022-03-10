package com.example.noteapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.model.NoteModel

class NoteAdapter(private val lists: List<NoteModel>, private val formattedDate: String) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = lists[position].noteTitle
        holder.description.text = lists[position].noteDetails

        when (lists[position].importance) {
            0 -> holder.cardview.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.cardview.context,
                    R.color.low_card_background
                )
            )

            1 -> holder.cardview.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.cardview.context,
                    R.color.medium_card_background
                )
            )

            2 -> holder.cardview.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.cardview.context,
                    R.color.high_card_background
                )
            )
        }

        when (lists[position].noteDate) {
            formattedDate -> holder.date.text = lists[position].noteTime
            else -> holder.date.text = lists[position].noteDate
        }

    }

    override fun getItemCount(): Int {
        return lists.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val title: TextView = ItemView.findViewById(R.id.tv_noteTitle)
        val description: TextView = ItemView.findViewById(R.id.tv_noteDescription)
        val date: TextView = ItemView.findViewById(R.id.tv_noteDate)
        val cardview: CardView = ItemView.findViewById(R.id.cardView_note)
    }

}