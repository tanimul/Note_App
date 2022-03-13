package com.example.noteapp.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.utils.Constants
import com.example.noteapp.R
import com.example.noteapp.adapter.NoteAdapter
import com.example.noteapp.databinding.ActivityHomeBinding
import com.example.noteapp.extentions.toast
import com.example.noteapp.data.listner.OnNoteClickListener
import com.example.noteapp.data.model.NoteModel
import com.example.noteapp.viewmodel.NoteViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppBaseActivity(), OnNoteClickListener {
    private val TAG = "HomeActivity"
    private lateinit var binding: ActivityHomeBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteList: ArrayList<NoteModel>
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var formattedDate: String
    private var optionMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get current date and time
        formattedDate =
            SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date())
        Log.d(TAG, "onCreate: $formattedDate")


        //note viewModel initialize
        noteViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        //Sett toolbar
        setToolbarWithoutBackButton(binding.toolbarLayout.toolbar)
        title = null

        //arrayList initialize
        noteList = ArrayList<NoteModel>()

        noteAdapter = NoteAdapter(noteList, noteList, formattedDate, this);


        //recyclerView
        binding.rvNoteList.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rvNoteList.adapter = noteAdapter

        //showNotes
        showNotes()


        //go to the Input Activity
        binding.fabInput.setOnClickListener {
            // launchActivity<InputActivity>()
            val intent = Intent(this, InputActivity::class.java)
            noteActResult.launch(intent)
        }


        binding.etGetYourImportantNote.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                Log.d(TAG, "onQueryTextChange: $s")
                noteAdapter.filter.filter(s)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {

                // filter your list from your input

                //you can use runnable postDelayed like 500 ms to delay search text
            }
        })


        deleteForSwipe()
    }

    private val noteActResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            isEmptyNote()
            Log.d(TAG, "Result Code: " + it.resultCode)
            if (it.resultCode != RESULT_CANCELED) {
                val noteModel: NoteModel = it.data?.getSerializableExtra("noteModel") as NoteModel
                if (it.resultCode == Constants.RequestCodes.REQUEST_CODE_ADD_NOTE) {
                    Log.d(TAG, "ok Add: " + it.data?.getSerializableExtra("noteModel"))
                    if (it.data != null) {
                        noteViewModel.addSingleNote(noteModel)
                        toast("Note Saved")
                    }

                } else if (it.resultCode == Constants.RequestCodes.REQUEST_CODE_EDIT_NOTE) {
                    Log.d(
                        TAG,
                        "ok Edit: " + it.data?.getSerializableExtra("noteModel") + " -> and id is: " + it.data?.getIntExtra(
                            "existingNoteId", -1
                        )
                    )
                    if (it.data != null) {
                        val id = it.data?.getIntExtra("existingNoteId", -1)
                        if (id != null) {
                            noteModel.id = id
                        }
                        noteViewModel.updateExistingNote(noteModel)
                        toast("Note Saved")
                    }

                }
            }

        }


    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (!isEmptyNote()) {
            val menuInflater = menuInflater
            menuInflater.inflate(R.menu.top_menu, menu)
            optionMenu = menu
            if (menu is MenuBuilder) {
                menu.setOptionalIconsVisible(true)
            }

            optionMenu?.findItem(R.id.menu_gridView)?.isVisible =
                binding.rvNoteList.layoutManager is LinearLayoutManager
            optionMenu?.findItem(R.id.menu_listView)?.isVisible =
                binding.rvNoteList.layoutManager is StaggeredGridLayoutManager

            Log.d(TAG, "onCreateOptionsMenu: " + binding.rvNoteList.layoutManager)

        }

        return true
    }

    private fun showNotes() {
        noteViewModel.showAllNotes.observe(
            this
        ) {
            noteList.clear()
            noteList.addAll(it)
            noteAdapter.notifyDataSetChanged()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: " + item.itemId)
        when (item.itemId) {
            R.id.menu_listView -> {
                binding.rvNoteList.layoutManager = LinearLayoutManager(this)
                // item.isVisible = false
                //  optionMenu?.findItem(R.id.menu_gridView)?.isVisible = true
            }
            R.id.menu_gridView -> {
                binding.rvNoteList.layoutManager =
                    StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                // item.isVisible = false

                // optionMenu?.findItem(R.id.menu_listView)?.isVisible = true
            }
            R.id.menu_help -> {
            }

            R.id.menu_delete -> {
                //delete Note
                deleteAll()
            }
//            R.id.menu_setting -> {
//                // dialog.show()
//            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAll() {
        AlertDialog.Builder(this@HomeActivity)
            .setTitle("Delete All")
            .setMessage("Are you sure you want to delete Tasks?")
            .setPositiveButton(
                "OK"
            ) { paramDialogInterface, paramInt -> noteViewModel.deleteAllNotes() }
            .setNegativeButton(
                "CANCEL"
            ) { dialog, which -> dialog.dismiss() }
            .show()

    }

    override fun onItemClick(noteModel: NoteModel) {
        Log.d(TAG, "onItemClick: $noteModel and id is: " + noteModel.id)
        noteActResult.launch(
            Intent(this, InputActivity::class.java).putExtra(
                "noteModel",
                noteModel as Serializable
            )
        )
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
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.deleteSingleNote(noteList[viewHolder.adapterPosition])
                noteAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
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
                            this@HomeActivity,
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

    private fun isEmptyNote(): Boolean {
        invalidateOptionsMenu()
        var noNote: Boolean = true
        noteViewModel.showAllNotes.observe(
            this
        ) {
            if (it.isEmpty()) {
                noNote = true
                binding.tvStatus.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
                binding.etGetYourImportantNote.visibility =
                    if (it.isEmpty()) View.INVISIBLE else View.VISIBLE


            } else {
                noNote = false
                binding.tvStatus.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
                binding.etGetYourImportantNote.visibility =
                    if (it.isEmpty()) View.INVISIBLE else View.VISIBLE
            }
        }
        return when (noNote) {
            true -> true
            false -> false
        }
    }
}

