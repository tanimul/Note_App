package com.example.noteapp.extentions

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T : Any> newIntent(context: Context): Intent = Intent(context, T::class.java)

inline fun <reified T : Any> Activity.launchActivity() {
    startActivity(newIntent<T>(this))
}
