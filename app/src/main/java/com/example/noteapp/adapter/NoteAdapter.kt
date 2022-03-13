package com.example.noteapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.interfaces.OnNoteClickListener
import com.example.noteapp.model.NoteModel


class NoteAdapter(
    var lists: List<NoteModel>,
    noteList: ArrayList<NoteModel>,
    private val formattedDate: String,
    onNoteClickListener: OnNoteClickListener
) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>(), Filterable {
    private val TAG = "NoteAdapter"
    private val onNoteClickListener: OnNoteClickListener = onNoteClickListener
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
            0 -> holder.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.cardView.context,
                    R.color.low_card_background
                )
            )

            1 -> holder.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.cardView.context,
                    R.color.medium_card_background
                )
            )

            2 -> holder.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.cardView.context,
                    R.color.high_card_background
                )
            )
        }

        when (lists[position].noteDate) {
            formattedDate -> holder.date.text = lists[position].noteTime
            else ->
                holder.date.text = lists[position].noteDate
        }

        holder.itemView.setOnClickListener {
            Log.d(TAG, "onBindViewHolder: " + lists[holder.absoluteAdapterPosition])
            onNoteClickListener.onItemClick(lists[holder.absoluteAdapterPosition])
        }


    }

    override fun getItemCount(): Int {
        return lists.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val title: TextView = ItemView.findViewById(R.id.tv_noteTitle)
        val description: TextView = ItemView.findViewById(R.id.tv_noteDescription)
        val date: TextView = ItemView.findViewById(R.id.tv_noteDate)
        val cardView: CardView = ItemView.findViewById(R.id.cardView_note)


    }

    override fun getFilter(): Filter {
        return quearyfilter
    }

    var quearyfilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val results = FilterResults()
            if (constraint == null || constraint.isEmpty()) {
                results.count = noteList.size
                results.values = noteList
            } else {
                val searchStr = constraint.toString().uppercase()
                val resultsData: MutableList<NoteModel> = ArrayList()
                for (noteInfo in noteList) {
                    if (noteInfo.noteTitle.uppercase()
                            .contains(searchStr)
                    ) resultsData.add(noteInfo)
                }
                results.count = resultsData.size
                results.values = resultsData
            }
            Log.d(TAG, "result count: " + results.count)
            Log.d(TAG, "result values: " + results.values)
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            lists = results.values as ArrayList<NoteModel>
            Log.d(TAG, "publishResults: " + lists.size)
            notifyDataSetChanged()
        }
    }

}