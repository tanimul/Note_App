package com.example.noteapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.R
import com.example.noteapp.databinding.ActivityHomeBinding
import com.example.noteapp.extentions.launchActivity
import com.example.noteapp.model.NoteModel
import com.example.noteapp.viewmodel.NoteViewModel


class HomeActivity : AppCompatActivity() {
    private val TAG = "HomeActivity"
    private lateinit var binding: ActivityHomeBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteList: ArrayList<NoteModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Sett toolbar
        setSupportActionBar(binding.toolbarLayout.toolbar)
        title = null

        //note viewModel initialize
        noteViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        //arrayList initialize
        noteList = ArrayList<NoteModel>()

        //recyclerView
        binding.rvNoteList.layoutManager = LinearLayoutManager(this)

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
            Log.d(TAG, "showNotes: ")
        }
    }

}