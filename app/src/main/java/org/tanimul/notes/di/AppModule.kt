package org.tanimul.notes.di

import android.app.Application
import androidx.annotation.Keep
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.tanimul.notes.data.database.NoteDatabase


@Module
@InstallIn(SingletonComponent::class)
@Keep
object AppModule {
    @Provides
    fun provideRoomInstance(application: Application): NoteDatabase {
        return NoteDatabase.getDatabase(application)
    }
}