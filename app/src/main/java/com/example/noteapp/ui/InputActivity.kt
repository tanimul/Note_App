package com.example.noteapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.R
import com.example.noteapp.data.model.NoteModel
import com.example.noteapp.databinding.ActivityInputBinding
import com.example.noteapp.utils.Constants
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

        binding.tvNoteTitle.text = if (intent.extras != null) {
            getText(R.string.update_note).toString()
        } else {
            getText(R.string.add_note).toString()
        }

        if (intent.extras != null) {
            existingNoteModel = intent.extras?.getSerializable("noteModel") as NoteModel
            Log.d(TAG, "onCreate: $existingNoteModel")
            binding.etTitle.setText(existingNoteModel.noteTitle)
            binding.etDescription.setText(existingNoteModel.noteDetails)
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

        binding.tvDateTime.text =
            SimpleDateFormat("EEEE, dd MMMM yyyy hh:mm a", Locale.getDefault()).format(
                Date()
            )

        binding.ivSaveNote.setOnClickListener {
            saveNote()
        }

        binding.icBack.setOnClickListener {
            saveNote()
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
                importance = 1
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

    override fun onBackPressed() {
        saveNote()
        super.onBackPressed()
    }
}

