package org.tanimul.notes.di

import android.app.Application
import androidx.annotation.Keep
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.tanimul.notes.data.database.NoteDatabase
import org.tanimul.notes.ui.fragments.editor.data.EditorRepositoryImpl
import org.tanimul.notes.ui.fragments.editor.domain.repository.EditorRepository
import org.tanimul.notes.ui.fragments.notes.data.NotesRepositoryImpl
import org.tanimul.notes.ui.fragments.notes.domain.repository.NotesRepository

@Module
@InstallIn(SingletonComponent::class)
@Keep
object AppModule {

    @Provides
    fun provideRoomInstance(application: Application): NoteDatabase {
        return NoteDatabase.getDatabase(application)
    }

    @Provides
    fun provideNotesRepository(notesRepositoryImpl: NotesRepositoryImpl): NotesRepository =
        notesRepositoryImpl

    @Provides
    fun provideEditorRepository(editorRepositoryImpl: EditorRepositoryImpl): EditorRepository =
        editorRepositoryImpl

}