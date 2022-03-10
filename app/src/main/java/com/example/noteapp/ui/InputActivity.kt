package com.example.noteapp.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.R
import com.example.noteapp.databinding.ActivityInputBinding
import com.example.noteapp.model.NoteModel
import com.example.noteapp.viewmodel.NoteViewModel

class InputActivity : AppBaseActivity() {
    private val TAG = "InputActivity"
    private lateinit var binding: ActivityInputBinding
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar(binding.toolbarLayout.toolbar)
        title = getText(R.string.add_note).toString()

        //note viewModel initialize
        noteViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        //Spinner
        spinnerPriorityTypes()

        binding.btSave.setOnClickListener {
            saveNote()
        }

    }

    private fun saveNote() {
        val noteModel = NoteModel(
            NoteTitle = binding.etTitle.text.toString(),
            noteDetails = binding.etDescription.text.toString(),
            noteTime = "Thursday, March 10, 2022 12:52 PM",
            importance = 2)

        noteViewModel.addSingleNote(noteModel)

    }

    private fun spinnerPriorityTypes() {
        val priorityAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.priority_types)
        )
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPriority.adapter = priorityAdapter
    }

}
