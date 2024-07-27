package org.tanimul.notes.di

import android.app.Application
import androidx.annotation.Keep
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.tanimul.notes.data.database.NoteDatabase
import org.tanimul.notes.ui.fragments.editor.data.AddNoteRepositoryImpl
import org.tanimul.notes.ui.fragments.editor.data.DeleteNoteRepositoryImpl
import org.tanimul.notes.ui.fragments.editor.data.UpdateNoteRepositoryImpl
import org.tanimul.notes.ui.fragments.editor.domain.repository.AddNoteRepository
import org.tanimul.notes.ui.fragments.editor.domain.repository.DeleteNoteRepository
import org.tanimul.notes.ui.fragments.editor.domain.repository.UpdateNoteRepository
import org.tanimul.notes.ui.fragments.notes.data.DeleteAllNoteRepositoryImpl
import org.tanimul.notes.ui.fragments.notes.data.NotesRepositoryImpl
import org.tanimul.notes.ui.fragments.notes.domain.repository.DeleteAllNoteRepository
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
    fun provideAddNoteRepository(addNoteRepositoryImpl: AddNoteRepositoryImpl): AddNoteRepository =
        addNoteRepositoryImpl

    @Provides
    fun provideUpdateNoteRepository(updateNoteRepositoryImpl: UpdateNoteRepositoryImpl): UpdateNoteRepository =
        updateNoteRepositoryImpl

    @Provides
    fun provideDeleteNoteRepository(deleteNoteRepositoryImpl: DeleteNoteRepositoryImpl): DeleteNoteRepository =
        deleteNoteRepositoryImpl

    @Provides
    fun provideNotesRepository(notesRepositoryImpl: NotesRepositoryImpl): NotesRepository =
        notesRepositoryImpl

/*    @Provides
    fun provideDeleteNoteRepository(deleteNoteRepositoryImpl: org.tanimul.notes.ui.fragments.notes.data.DeleteNoteRepositoryImpl): org.tanimul.notes.ui.fragments.notes.domain.repository.DeleteNoteRepository =
        deleteNoteRepositoryImpl*/

    @Provides
    fun provideDeleteAllNoteRepository(deleteAllNoteRepositoryImpl: DeleteAllNoteRepositoryImpl): DeleteAllNoteRepository =
        deleteAllNoteRepositoryImpl
}