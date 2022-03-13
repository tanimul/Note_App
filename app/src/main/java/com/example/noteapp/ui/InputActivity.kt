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
    private lateinit var existingNoteModel: NoteModel
    private var createdAt: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.extras != null) {
            binding.tvNoteTitle.text = getText(R.string.update_note).toString()
        } else {
            createdAt = System.currentTimeMillis()
            binding.tvNoteTitle.text = getText(R.string.add_note).toString()
            binding.tvDateTime.text =
                SimpleDateFormat("EEEE, dd MMMM yyyy hh:mm a", Locale.getDefault()).format(Date())
        }

        if (intent.extras != null) {
            existingNoteModel = intent.extras?.getSerializable("noteModel") as NoteModel
            Log.d(TAG, "onCreate: $existingNoteModel")
            binding.etTitle.setText(existingNoteModel.noteTitle)
            binding.etDescription.setText(existingNoteModel.noteDetails)
            createdAt = existingNoteModel.addedAt
            binding.tvDateTime.text =
                SimpleDateFormat("EEEE, dd MMMM yyyy hh:mm a", Locale.getDefault()).format(
                    existingNoteModel.addedAt
                )
        }

        //note viewModel initialize
        noteViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

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
                addedAt = createdAt,
                updatedAt = System.currentTimeMillis(),
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

