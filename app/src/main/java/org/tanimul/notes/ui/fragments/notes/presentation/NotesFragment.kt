package org.tanimul.notes.ui.fragments.notes.presentation

import android.app.AlertDialog
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
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
import org.tanimul.notes.base.BaseFragment
import org.tanimul.notes.common.extentions.launchAndRepeatWithViewLifecycle
import org.tanimul.notes.common.extentions.toast
import org.tanimul.notes.databinding.FragmentNotesBinding
import timber.log.Timber

@AndroidEntryPoint
class NotesFragment : BaseFragment<FragmentNotesBinding>(R.layout.fragment_notes) {

    private val notesViewModel: NotesViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = notesViewModel

        launchAndRepeatWithViewLifecycle {
            launch {
                notesViewModel.uiAction.collectLatest {
                    when (it) {
                        is NotesUiActions.NavigateBack -> {}
                        is NotesUiActions.NavigateEditorScreen -> {
                            findNavController().navigate(
                                NotesFragmentDirections.actionNotesFragmentToInputFragment(
                                    null
                                )
                            )
                        }

                        is NotesUiActions.ShowMenu -> {
                            showMenu()
                        }

                        is NotesUiActions.SelectedNote -> {
                            findNavController().navigate(
                                NotesFragmentDirections.actionNotesFragmentToInputFragment(
                                    it.note
                                )
                            )
                        }
                    }
                }
            }
        }


        mBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Timber.d("onQueryTextChange: $s")
                notesViewModel.searchNotes("$s")
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        })

        deleteForSwipe()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_listView -> {
                mBinding.rvNoteList.layoutManager = LinearLayoutManager(requireContext())
            }

            R.id.menu_gridView -> {
                mBinding.rvNoteList.layoutManager =
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

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showMenu() {   // Create and configure the PopupMenu
        val popup = PopupMenu(requireContext(), mBinding.ibDropdownMenu).apply {
            menuInflater.inflate(R.menu.top_menu, menu)

            // Set item visibility based on LayoutManager type
            menu.findItem(R.id.menu_gridView).isVisible =
                mBinding.rvNoteList.layoutManager is LinearLayoutManager
            menu.findItem(R.id.menu_listView).isVisible =
                mBinding.rvNoteList.layoutManager is StaggeredGridLayoutManager

            setForceShowIcon(true)

            // Set the click listener for menu items
            setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
        }

        // Show the PopupMenu
        popup.show()
    }

    private fun deleteAll() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete_all))
            .setMessage(getString(R.string.are_you_sure_you_want_to_delete_tasks))
            .setPositiveButton(
                getString(R.string.ok)
            ) { _, _ -> notesViewModel.deleteNotes() }
            .setNegativeButton(
                getString(R.string.cancel)
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
                notesViewModel.deleteNote(viewHolder.adapterPosition)
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
                )
            }
        }).attachToRecyclerView(mBinding.rvNoteList)
    }

}