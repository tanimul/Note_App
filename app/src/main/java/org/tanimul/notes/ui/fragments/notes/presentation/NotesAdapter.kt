package org.tanimul.notes.ui.fragments.notes.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.tanimul.notes.common.domain.model.NoteModel
import org.tanimul.notes.databinding.ItemNoteBinding
import timber.log.Timber

class NotesAdapter(val viewModel: NotesViewModel) :
    ListAdapter<NoteModel, NotesAdapter.NotesViewHolder>(DiffCallBack()) {
    class NotesViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: NoteModel, notesViewModel: NotesViewModel) {
            binding.apply {
                note = data
                viewModel = notesViewModel
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): NotesViewHolder {
        return NotesViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.onBind(getItem(position), viewModel)
    }

    private class DiffCallBack : DiffUtil.ItemCallback<NoteModel>() {
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean =
            oldItem == newItem
    }
}

@BindingAdapter(value = ["bindNotes", "bindViewModel"], requireAll = true)
fun RecyclerView.bindNotes(
    data: List<NoteModel>?, viewModel: NotesViewModel
) {
    Timber.d("onBind: $data")
    if (adapter == null) adapter = NotesAdapter(viewModel)
    val value = data ?: emptyList()
    val adapter = adapter as? NotesAdapter
    adapter?.submitList(value)
}