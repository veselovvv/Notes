package com.veselovvv.notes.viewmodels

import androidx.lifecycle.ViewModel
import com.veselovvv.notes.models.Note
import com.veselovvv.notes.repositories.NoteRepository

class NoteListViewModel : ViewModel() {
    private val noteRepository = NoteRepository.get()
    val noteListLiveData = noteRepository.getNotes()

    fun addNote(note: Note) = noteRepository.addNote(note)
}