package com.veselovvv.notes

import androidx.lifecycle.ViewModel

class NoteListViewModel : ViewModel() {

    private val noteRepository = NoteRepository.get()

    val noteListLiveData = noteRepository.getNotes()

    fun addNote(note: Note) {
        noteRepository.addNote(note)
    }
}