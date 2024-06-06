package org.tanimul.notes.ui.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.tanimul.notes.R
import org.tanimul.notes.adapter.NoteAdapter
import org.tanimul.notes.base.BaseActivity
import org.tanimul.notes.data.model.NoteModel
import org.tanimul.notes.databinding.ActivityHomeBinding
import org.tanimul.notes.utils.Constants.REQUEST_CODE_ADD_NOTE
import org.tanimul.notes.utils.Constants.REQUEST_CODE_EDIT_NOTE
import org.tanimul.notes.viewmodel.NoteViewModel
import java.io.Serializable
import java.util.*

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun getViewBinding(): ActivityHomeBinding {
        return  ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun init(savedInstanceState: Bundle?) {
    }


}