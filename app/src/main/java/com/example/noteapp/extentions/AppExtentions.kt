package com.example.noteapp.extentions

import android.content.Context
import android.content.Intent

inline fun <reified T : Any> Context.launchActivity() {
    startActivity(Intent(this, T::class.java))
}