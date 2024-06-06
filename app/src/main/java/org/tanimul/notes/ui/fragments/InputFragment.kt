package org.tanimul.notes.ui.fragments

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import org.tanimul.notes.R
import org.tanimul.notes.base.BaseFragment
import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.databinding.FragmentInputBinding
import org.tanimul.notes.utils.toast
import org.tanimul.notes.viewmodel.NoteViewModel

@AndroidEntryPoint
class InputFragment : BaseFragment<FragmentInputBinding>() {

    private val noteViewModel: NoteViewModel by viewModels()
    private val args: InputFragmentArgs by navArgs()


    private lateinit var existingNoteModel: NoteModel
    private var createdAt: Long = 0L
    private var priorityCode = 0
    private var dialogDeleteNote: AlertDialog? = null


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentInputBinding = DataBindingUtil.inflate(
        layoutInflater, R.layout.fragment_input, container, false
    )

    override fun init() {

        binding.note = args.noteModel


        //test

        if (args.noteModel != null) {
            existingNoteModel = args.noteModel!!
//            Log.d(InputActivity.TAG, "onCreate: $existingNoteModel")
            binding.etTitle.setText(existingNoteModel.noteTitle)
            binding.etDescription.setText(existingNoteModel.noteDetails)
            createdAt = existingNoteModel.addedAt
            priorityCode = existingNoteModel.importance
//            binding.tvDateTime.text =
//                SimpleDateFormat("EEEE, dd MMMM yyyy hh:mm a", Locale.getDefault()).format(
//                    existingNoteModel.addedAt
//                )
        }

        initMiscellaneous()

        binding.ivSaveNote.setOnClickListener {
            saveNote()
        }

        binding.icBack.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        if (binding.etTitle.text.toString().isNotEmpty() && binding.etDescription.text.toString()
                .isNotEmpty()
        ) {

            val noteModel = NoteModel(
                noteTitle = binding.etTitle.text.toString(),
                noteDetails = binding.etDescription.text.toString(),
                addedAt = createdAt,
                updatedAt = System.currentTimeMillis(),
                importance = priorityCode
            )

            if (args.noteModel != null) noteModel.id = args.noteModel!!.id
            noteViewModel.addSingleNote(noteModel)


        }
        findNavController().popBackStack()
    }


    private fun initMiscellaneous() {
        val bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
        val miscellaneous: LinearLayout = binding.layoutMiscellaneous.root
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


        if (activity?.intent?.extras != null) {
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
            val builder = AlertDialog.Builder(requireContext())
            val view: View = layoutInflater.inflate(R.layout.layout_delete_note, null)
            builder.setView(view)
            dialogDeleteNote = builder.create()
            if (dialogDeleteNote!!.window != null) {
                dialogDeleteNote!!.window!!.setBackgroundDrawable(ColorDrawable(0))
            }
            view.findViewById<View>(R.id.textDeleteNote).setOnClickListener {
                noteViewModel.deleteSingleNote(existingNoteModel)
                dialogDeleteNote!!.dismiss()
                activity?.toast("Note deleted successfully")
                findNavController().popBackStack()
            }
            view.findViewById<View>(R.id.textCancel)
                .setOnClickListener { dialogDeleteNote!!.dismiss() }
        }
        dialogDeleteNote!!.show()
    }

    private fun setStatusBarColor(colorCode: Int) {
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.statusBarColor = ContextCompat.getColor(this, colorCode)
    }

}