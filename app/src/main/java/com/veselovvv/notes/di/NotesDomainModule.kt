package com.veselovvv.notes.di

import com.veselovvv.notes.data.NoteRepository
import com.veselovvv.notes.ui.NotesViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class NotesDomainModule {
    @Provides
    fun provideNotesViewModel(
        repository: NoteRepository
    ): NotesViewModel = NotesViewModel(repository)
}