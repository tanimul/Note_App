package org.tanimul.notes.ui.fragments.notes.presentation

import android.app.AlertDialog
import android.graphics.Canvas
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.tanimul.notes.R
import org.tanimul.notes.adapter.NoteAdapter
import org.tanimul.notes.base.BaseFragment
import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val noteViewModel: HomeViewModel by viewModels()
    private lateinit var notes: ArrayList<NoteModel>
    private lateinit var noteAdapter: NoteAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeBinding = DataBindingUtil.inflate(
        layoutInflater, R.layout.fragment_home, container, false
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun init() {
        notes = ArrayList<NoteModel>()

        noteAdapter = NoteAdapter(notes, notes) {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToInputFragment(it))
        }


        //recyclerView
        binding.rvNoteList.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rvNoteList.adapter = noteAdapter

        showNotes()

        //go to the Input Activity
        binding.fabInput.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToInputFragment(
                    null
                )
            )
        }

        binding.ibDropdownMenu.setOnClickListener {
            val popup = PopupMenu(requireContext(), it)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.top_menu, popup.menu)

            popup.menu.findItem(R.id.menu_gridView)?.isVisible =
                binding.rvNoteList.layoutManager is LinearLayoutManager
            popup.menu.findItem(R.id.menu_listView)?.isVisible =
                binding.rvNoteList.layoutManager is StaggeredGridLayoutManager
            popup.setForceShowIcon(true)

            popup.setOnMenuItemClickListener { item ->
                onOptionsItemSelected(item)
            }
            popup.show()
        }


        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //Log.d(HomeActivity.TAG, "onQueryTextChange: $s")
                ///noteAdapter.filter.filter(s)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        })

        deleteForSwipe()
    }

    private fun showNotes() {
        lifecycleScope.launch {
            noteViewModel.fetchNotes.collectLatest {
                it?.let {
                    notes.clear()
                    notes.addAll(it)
                    binding.emptyLayout.root.isVisible = it.isEmpty()
                    binding.etSearch.isVisible = it.isNotEmpty()
                    noteAdapter.notifyDataSetChanged()
                }

            }
        }
    }

    //  }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //   Log.d(HomeActivity.TAG, "onOptionsItemSelected: " + item.itemId)
        when (item.itemId) {
            R.id.menu_listView -> {
                binding.rvNoteList.layoutManager = LinearLayoutManager(requireContext())
            }

            R.id.menu_gridView -> {
                binding.rvNoteList.layoutManager =
                    StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            }

            R.id.menu_help -> {
            }

            R.id.menu_delete -> {
                //delete Note
                deleteAll()
            }

            R.id.menu_setting -> {
                // dialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAll() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete All")
            .setMessage("Are you sure you want to delete Tasks?")
            .setPositiveButton(
                "OK"
            ) { _, _ -> noteViewModel.deleteNotes() }
            .setNegativeButton(
                "CANCEL"
            ) { dialog, _ -> dialog.dismiss() }
            .show()

    }

    //Delete by left to right swipe
    private fun deleteForSwipe() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.UP or ItemTouchHelper.DOWN
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.deleteNote(notes[viewHolder.adapterPosition])
                ///noteAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean,
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorBackground
                        )
                    )
                    .addActionIcon(R.drawable.ic_delete_red)
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                );
            }
        }).attachToRecyclerView(binding.rvNoteList)
    }

}