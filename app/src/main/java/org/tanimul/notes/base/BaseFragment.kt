package org.tanimul.notes.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import org.tanimul.notes.ui.dialogs.LoadingDialog

abstract class BaseFragment<E : ViewBinding> : Fragment() {

    private var _binding: E? = null
    private var mContext: Context? = null

    private var loadingDialog: LoadingDialog? = null

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): E
    protected abstract fun init()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = getViewBinding(inflater, container)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    val binding: E
        get() = _binding!!

    fun initLoadingDialog() {
        loadingDialog = LoadingDialog(mContext)
    }

    fun showLoadingDialog() {
        loadingDialog?.show()
    }

    fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
    }
}

