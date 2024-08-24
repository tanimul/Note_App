package org.tanimul.notes.ui.fragments.editor.presentation

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.tanimul.notes.R
import org.tanimul.notes.base.BaseFragment
import org.tanimul.notes.common.domain.model.NoteModel
import org.tanimul.notes.common.extentions.toast
import org.tanimul.notes.databinding.FragmentEditorBinding

@AndroidEntryPoint
class EditorFragment : BaseFragment<FragmentEditorBinding>() {

    private val editorViewModel: EditorViewModel by viewModels()
    private val args: EditorFragmentArgs by navArgs()

    private var priorityCode = 0
    private var dialogDeleteNote: AlertDialog? = null


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentEditorBinding = DataBindingUtil.inflate(
        layoutInflater, R.layout.fragment_editor, container, false
    )

    override fun init() {

        binding.apply {
            note = args.noteModel
            viewModel = editorViewModel
        }

        args.noteModel?.let { priorityCode = it.importance }

        initMiscellaneous()

        lifecycleScope.launch {
            launch {
                editorViewModel.uiAction.collectLatest {
                    when (it) {
                        is InputUiActions.AddNote -> saveNote()
                        is InputUiActions.DeleteNote -> {}
                        is InputUiActions.NavigateBack -> saveNote()
                        is InputUiActions.UpdateNote -> saveNote()
                    }
                }
            }
        }
    }

    private fun saveNote() {
        if (binding.etTitle.text.toString().isNotEmpty() || binding.etDescription.text.toString()
                .isNotEmpty()
        ) {

            val noteModel = NoteModel(
                noteTitle = binding.etTitle.text.toString(),
                noteDetails = binding.etDescription.text.toString(),
                updatedAt = System.currentTimeMillis(),
                importance = priorityCode
            )
            if (args.noteModel != null) {
                noteModel.id = args.noteModel!!.id
                editorViewModel.updateNote(noteModel)
            } else {
                editorViewModel.addNote(noteModel)
            }


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


        if (args.noteModel != null) {
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
                // args.noteModel?.let { it1 -> noteViewModel.deleteSingleNote(it1) }
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
        activity?.window?.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = ContextCompat.getColor(requireContext(), colorCode)
        }
    }

}