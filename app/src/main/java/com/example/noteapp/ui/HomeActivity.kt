package com.example.noteapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.noteapp.databinding.ActivityHomeBinding
import com.example.noteapp.extentions.launchActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val TAG = "HomeActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarLayout.toolbar.title = "Home"

        binding.fabAdd.setOnClickListener {
            launchActivity<InputActivity>()
        }
    }
}