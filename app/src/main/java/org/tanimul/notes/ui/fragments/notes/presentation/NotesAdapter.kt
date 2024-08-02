package org.tanimul.notes.ui.fragments.notes.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.tanimul.notes.common.domain.model.NoteModel
import org.tanimul.notes.databinding.ItemNoteBinding

@OptIn(ExperimentalCoroutinesApi::class)
class NotesAdapter() :
    ListAdapter<NoteModel, NotesAdapter.ContentViewHolder>(DiffCallBack()) {
    class ContentViewHolder(val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: NoteModel) {
            binding.note = data
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ContentViewHolder {
        return ContentViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }


    private class DiffCallBack : DiffUtil.ItemCallback<NoteModel>() {
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean =
            oldItem.id == newItem.id
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@BindingAdapter(value = ["bindNote"], requireAll = true)
fun RecyclerView.bindContent(
    data: List<NoteModel>?
) {
    if (adapter == null) adapter = NotesAdapter()
    val value = data ?: emptyList()
    val adapter = adapter as? NotesAdapter
    adapter?.submitList(value)

}