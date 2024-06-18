package org.tanimul.notes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.tanimul.notes.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class NoteApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // initialize timber in application class
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}