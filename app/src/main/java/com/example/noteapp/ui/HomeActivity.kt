package com.example.noteapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.view.menu.MenuBuilder
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.R
import com.example.noteapp.adapter.NoteAdapter
import com.example.noteapp.databinding.ActivityHomeBinding
import com.example.noteapp.extentions.launchActivity
import com.example.noteapp.model.NoteModel
import com.example.noteapp.viewmodel.NoteViewModel


class HomeActivity : AppBaseActivity() {
    private val TAG = "HomeActivity"
    private lateinit var binding: ActivityHomeBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteList: ArrayList<NoteModel>
    private lateinit var noteAdapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Sett toolbar
        setToolbarWithoutBackButton(binding.toolbarLayout.toolbar)
        title = null

        //note viewModel initialize
        noteViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        //arrayList initialize
        noteList = ArrayList<NoteModel>()

        noteAdapter = NoteAdapter(noteList);



        //recyclerView
        binding.rvNoteList.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        binding.rvNoteList.adapter = noteAdapter


        //showNotes
        showNotes()

        //go to the Input Activity
        binding.fabInput.setOnClickListener {
            launchActivity<InputActivity>()
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.top_menu, menu)
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        return true
    }

    private fun showNotes() {
        noteViewModel.showAllNotes.observe(
            this
        ) {
            Log.d(TAG, "showNotes: "+it.size)
            noteList.clear()
            noteList.addAll(it)
            noteAdapter.notifyDataSetChanged()
        }
    }

}