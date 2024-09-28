package com.veselovvv.notes

import android.content.Context
import androidx.room.Room
import com.veselovvv.notes.data.NoteDatabase
import com.veselovvv.notes.data.NoteRepository
import com.veselovvv.notes.di.NotesDataModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NotesDataModule::class] // replaces NotesDataModule with this fake one
)
class TestNotesDataModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.inMemoryDatabaseBuilder( // uses different database for tests
            context.applicationContext,
            NoteDatabase::class.java
        ).build()

    @Provides
    @Singleton
    fun provideNoteRepository(database: NoteDatabase): NoteRepository = NoteRepository(database)
}