package org.tanimul.notes.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<E: ViewBinding> : AppCompatActivity(){

    private var _binding: E? = null

    val binding: E get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding();
        setContentView(_binding!!.root)
        init(savedInstanceState)
    }


    protected abstract fun getViewBinding() : E
    protected abstract fun init(savedInstanceState: Bundle?)
}