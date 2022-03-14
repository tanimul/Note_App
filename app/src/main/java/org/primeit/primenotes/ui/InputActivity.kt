package org.primeit.primenotes.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.extentions.toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.primeit.primenotes.R
import org.primeit.primenotes.data.model.NoteModel
import org.primeit.primenotes.databinding.ActivityInputBinding
import org.primeit.primenotes.utils.Constants.REQUEST_CODE_ADD_NOTE
import org.primeit.primenotes.utils.Constants.REQUEST_CODE_EDIT_NOTE
import org.primeit.primenotes.viewmodel.NoteViewModel
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


class InputActivity : AppBaseActivity() {

    private val TAG = "InputActivity"
    private lateinit var binding: ActivityInputBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var existingNoteModel: NoteModel
    private var createdAt: Long = 0L
    private var priorityCode = 0
    private var dialogDeleteNote: AlertDialog? = null

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
            priorityCode = existingNoteModel.importance
            binding.tvDateTime.text =
                SimpleDateFormat("EEEE, dd MMMM yyyy hh:mm a", Locale.getDefault()).format(
                    existingNoteModel.addedAt
                )
        }

        //note viewModel initialize
        noteViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        initMiscellaneous()

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
                importance = priorityCode
            )
            // noteViewModel.addSingleNote(noteModel)
            val resultIntent = Intent()

            if (intent.extras != null) {
                resultIntent.putExtra(
                    "noteModel", noteModel as Serializable
                )
                resultIntent.putExtra("existingNoteId", existingNoteModel.id)
                setResult(
                    REQUEST_CODE_EDIT_NOTE, resultIntent
                )
                Log.d(TAG, "editNote: ")

            } else {

                setResult(
                    REQUEST_CODE_ADD_NOTE,
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

    private fun initMiscellaneous() {
        val bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
        val miscellaneous: LinearLayout = findViewById(R.id.miscellaneous)
        bottomSheetBehavior = BottomSheetBehavior.from(miscellaneous)
        miscellaneous.setOnClickListener {
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
        }
        val imageColor1 = miscellaneous.findViewById<ImageView>(R.id.imageColor1)
        val imageColor2 = miscellaneous.findViewById<ImageView>(R.id.imageColor2)
        val imageColor3 = miscellaneous.findViewById<ImageView>(R.id.imageColor3)

        when (priorityCode) {
            0 -> {
                imageColor1.setImageResource(R.drawable.done_vector)
                binding.rootLayout.setBackgroundResource(R.color.colorBackground1)
                setStatusBarColor(R.color.colorBackground1)
            }
            1 -> {
                imageColor2.setImageResource(R.drawable.done_vector)
                binding.rootLayout.setBackgroundResource(R.color.colorBackground2)
                setStatusBarColor(R.color.colorBackground2)
            }
            2 -> {
                imageColor3.setImageResource(R.drawable.done_vector)
                binding.rootLayout.setBackgroundResource(R.color.colorBackground3)
                setStatusBarColor(R.color.colorBackground3)
            }
        }

        miscellaneous.findViewById<View>(R.id.imageColor1).setOnClickListener {
            priorityCode = 0
            imageColor1.setImageResource(R.drawable.done_vector)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(0)
            binding.rootLayout.setBackgroundResource(R.color.colorBackground1)
            setStatusBarColor(R.color.colorBackground1)
        }
        miscellaneous.findViewById<View>(R.id.imageColor2).setOnClickListener {
            priorityCode = 1
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(R.drawable.done_vector)
            imageColor3.setImageResource(0)
            binding.rootLayout.setBackgroundResource(R.color.colorBackground2)
            setStatusBarColor(R.color.colorBackground2)
        }
        miscellaneous.findViewById<View>(R.id.imageColor3).setOnClickListener {
            priorityCode = 2
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(R.drawable.done_vector)
            binding.rootLayout.setBackgroundResource(R.color.colorBackground3)
            setStatusBarColor(R.color.colorBackground3)
        }


        if (intent.extras != null) {
            miscellaneous.findViewById<View>(R.id.layoutDeleteNote).visibility = View.VISIBLE
            miscellaneous.findViewById<View>(R.id.layoutDeleteNote)
                .setOnClickListener {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    showDeleteDialog()
                }
        }
    }

    private fun showDeleteDialog() {
        if (dialogDeleteNote == null) {
            val builder = AlertDialog.Builder(this@InputActivity)
            val view: View = layoutInflater.inflate(R.layout.layout_delete_note, null)
            builder.setView(view)
            dialogDeleteNote = builder.create()
            if (dialogDeleteNote!!.window != null) {
                dialogDeleteNote!!.window!!.setBackgroundDrawable(ColorDrawable(0))
            }
            view.findViewById<View>(R.id.textDeleteNote).setOnClickListener {
                noteViewModel.deleteSingleNote(existingNoteModel)
                dialogDeleteNote!!.dismiss()
                toast("Note deleted successfully")
                finish()
            }
            view.findViewById<View>(R.id.textCancel)
                .setOnClickListener { dialogDeleteNote!!.dismiss() }
        }
        dialogDeleteNote!!.show()
    }

    private fun setStatusBarColor(colorCode: Int) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, colorCode)
    }
}

