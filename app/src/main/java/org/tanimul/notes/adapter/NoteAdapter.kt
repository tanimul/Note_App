package org.tanimul.notes.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.tanimul.notes.R
import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.databinding.LayoutNoteBinding
import java.text.SimpleDateFormat
import java.util.*


class NoteAdapter(
    var noteLists: List<NoteModel>,
    noteList: ArrayList<NoteModel>,
    private val onItemClicked: (NoteModel) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(), Filterable {
    private val TAG = "NoteAdapter"

    inner class NoteViewHolder(val binding: LayoutNoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_note,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        with(holder.binding) {
            with(noteLists[position]) {
                tvNoteTitle.isVisible = noteTitle.isNotEmpty()
                tvNoteDescription.isVisible = noteDetails.isNotEmpty()

                holder.binding.note=noteLists[position]
            }
        }

        when (noteLists[position].importance) {

            0 -> {
                with(holder.binding) {
                    viewImportance.setBackgroundResource(R.color.colorNote1)
                    cardViewNote.setCardBackgroundColor(
                        ContextCompat.getColor(
                            holder.itemView.context, R.color.colorBackground1
                        )
                    )
                }
            }

            1 -> {
                with(holder.binding) {
                    viewImportance.setBackgroundResource(R.color.colorNote2)
                    cardViewNote.setCardBackgroundColor(
                        ContextCompat.getColor(
                            holder.itemView.context, R.color.colorBackground2
                        )
                    )
                }
            }
            2 -> {
                with(holder.binding) {
                    viewImportance.setBackgroundResource(R.color.colorNote3)
                    cardViewNote.setCardBackgroundColor(
                        ContextCompat.getColor(
                            holder.itemView.context, R.color.colorBackground3
                        )
                    )
                }
            }
        }

        holder.itemView.setOnClickListener {
            Log.d(TAG, "onBindViewHolder: " + noteLists[holder.absoluteAdapterPosition])
            onItemClicked.invoke(noteLists[holder.absoluteAdapterPosition])
        }

    }

    override fun getItemCount(): Int {
        return noteLists.size
    }

    override fun getFilter(): Filter {
        return queryFilter
    }

    private var queryFilter: Filter = object : Filter() {
        override fun performFiltering(constSeq: CharSequence): FilterResults {
            val results = FilterResults()
            if (constSeq.isEmpty()) {
                results.count = noteList.size
                results.values = noteList
            } else {
                val searchStr = constSeq.toString().uppercase()
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
            noteLists = results.values as ArrayList<NoteModel>
            Log.d(TAG, "publishResults: " + noteLists.size)
            notifyDataSetChanged()
        }
    }

}