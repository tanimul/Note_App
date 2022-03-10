package com.example.noteapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.view.menu.MenuBuilder
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.R
import com.example.noteapp.adapter.NoteAdapter
import com.example.noteapp.databinding.ActivityHomeBinding
import com.example.noteapp.extentions.launchActivity
import com.example.noteapp.model.NoteModel
import com.example.noteapp.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppBaseActivity() {
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

        if (isNoNote()) {
            //Sett toolbar
            setToolbarWithoutBackButton(binding.toolbarLayout.toolbar)
            title = null
        } else {
            binding.toolbarLayout.toolbar.visibility = View.GONE
        }

        //arrayList initialize
        noteList = ArrayList<NoteModel>()

        noteAdapter = NoteAdapter(noteList, formattedDate);


        //recyclerView
        binding.rvNoteList.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rvNoteList.adapter = noteAdapter

        if (isNoNote()) {
            //showNotes
            showNotes()
        }

        //go to the Input Activity
        binding.fabInput.setOnClickListener {
            launchActivity<InputActivity>()
        }

    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isNoNote()) {
            val menuInflater = menuInflater
            menuInflater.inflate(R.menu.top_menu, menu)
            optionMenu = menu
            if (menu is MenuBuilder) {
                menu.setOptionalIconsVisible(true)
            }
            optionMenu?.findItem(R.id.menu_gridView)?.isVisible = false
        }

        return true
    }

    private fun showNotes() {
        noteViewModel.showAllNotes.observe(
            this
        ) {
            Log.d(TAG, "showNotes: " + it.size)
            noteList.clear()
            noteList.addAll(it)
            noteAdapter.notifyDataSetChanged()
        }
    }

    private fun isNoNote(): Boolean {
        var noNote: Boolean = true
        noteViewModel.showAllNotes.observe(
            this
        ) {
            Log.d(TAG, "NoNote check size: " + it.size)
            if (it.isEmpty()) {
                binding.svGetYourImportantNote.visibility = View.GONE
                noNote = false
            } else {
                noNote = true
            }
        }
        return when (noNote) {
            true -> true
            false -> false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.menu_listView -> {
                binding.rvNoteList.layoutManager = LinearLayoutManager(this)
                item.isVisible = false
                optionMenu?.findItem(R.id.menu_gridView)?.isVisible = true
            }
            R.id.menu_gridView -> {
                binding.rvNoteList.layoutManager =
                    StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                item.isVisible = false
                optionMenu?.findItem(R.id.menu_listView)?.isVisible = true

            }
            R.id.menu_help -> {

//        //delete Note
//        noteViewModel.deleteAllNotes()
            }

            R.id.menu_delete -> {
                //delete Note
                noteViewModel.deleteAllNotes()
            }
            R.id.menu_setting -> {
                // dialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}