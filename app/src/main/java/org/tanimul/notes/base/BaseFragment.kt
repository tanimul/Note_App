package org.tanimul.notes.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import org.tanimul.notes.R
import org.tanimul.notes.common.autoCleared
import org.tanimul.notes.ui.dialogs.LoadingDialog

abstract class BaseFragment<T : ViewDataBinding> constructor(@LayoutRes private val mContentLayoutId: Int) :
    Fragment() {

    var mBinding by autoCleared<T>()
    private var loadingDialog: LoadingDialog? = null

    var mToolbar: Toolbar? = null
        private set

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, mContentLayoutId, container, false)
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.root.filterTouchesWhenObscured = true

        // Default background color for status bar, can be overridden
        setStatusBarColor(R.color.colorBackground)
        return mBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loadingDialog?.dismiss() // Dismiss loading dialog if exists
        mToolbar = null
    }

    fun initLoadingDialog() {
        loadingDialog = LoadingDialog(requireContext())
    }

    fun showLoadingDialog() {
        loadingDialog?.show()
    }

    fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
    }

    protected open val resToolbarId: Int = 0
    protected open val haveToolbar: Boolean = false

    fun setStatusBarColor(colorCode: Int) {
        activity?.window?.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = ContextCompat.getColor(requireContext(), colorCode)
        }
    }
}
