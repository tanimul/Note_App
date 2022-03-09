package com.example.noteapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.noteapp.R
import com.example.noteapp.databinding.ActivityInputBinding

class InputActivity : AppCompatActivity() {
    private val TAG = "InputActivity"
    private lateinit var binding: ActivityInputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
