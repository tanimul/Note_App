package com.example.noteapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.Constants
import com.example.noteapp.R
import com.example.noteapp.databinding.ActivityInputBinding
import com.example.noteapp.extentions.toast
import com.example.noteapp.model.NoteModel
import com.example.noteapp.viewmodel.NoteViewModel
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class InputActivity : AppBaseActivity() {
    private val TAG = "InputActivity"
    private lateinit var binding: ActivityInputBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var formattedDate: String
    private lateinit var formattedTime: String
    private lateinit var existingNoteModel: NoteModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar(binding.toolbarLayout.toolbar)
        title = if (intent.extras != null) {
            getText(R.string.update_note).toString()
        } else {
            getText(R.string.add_note).toString()
        }

        //Spinner
        spinnerPriorityTypes()


        if (intent.extras != null) {
            existingNoteModel = intent.extras?.getSerializable("noteModel") as NoteModel
            Log.d(TAG, "onCreate: $existingNoteModel")
            binding.etTitle.setText(existingNoteModel.noteTitle)
            binding.etDescription.setText(existingNoteModel.noteDetails)
            binding.spinnerPriority.setSelection(existingNoteModel.importance);
        }

        //get current date and time
        formattedDate =
            SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date())
        Log.d(TAG, "onCreate: $formattedDate")

        formattedTime =
            SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        Log.d(TAG, "onCreate: $formattedTime")


        //note viewModel initialize
        noteViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        binding.btSave.setOnClickListener {

            saveNote()


        }

        binding.ibDropdown.setOnClickListener {
            binding.spinnerPriority.performClick()
        }
    }

    private fun saveNote() {
        if (binding.etTitle.text.toString().isEmpty() && binding.etDescription.text.toString()
                .isEmpty()
        ) {
            Log.d(TAG, "Note saveNote: ")
        } else {

            val noteModel = NoteModel(
                noteTitle = binding.etTitle.text.toString(),
                noteDetails = binding.etDescription.text.toString(),
                noteDate = formattedDate,
                noteTime = formattedTime,
                importance = binding.spinnerPriority.selectedItemPosition
            )
            // noteViewModel.addSingleNote(noteModel)
            val resultIntent = Intent()

            if (intent.extras != null) {
                resultIntent.putExtra(
                    "noteModel", noteModel as Serializable
                )
                resultIntent.putExtra("existingNoteId", existingNoteModel.id)
                setResult(
                    Constants.RequestCodes.REQUEST_CODE_EDIT_NOTE, resultIntent
                )
                Log.d(TAG, "editNote: ")

            } else {

                setResult(
                    Constants.RequestCodes.REQUEST_CODE_ADD_NOTE,
                    resultIntent.putExtra("noteModel", noteModel as Serializable)
                )
                Log.d(TAG, "addNote: ")
            }

        }


        finish()
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

    override fun onBackPressed() {
        saveNote()
        super.onBackPressed()
    }
}

