package com.veselovvv.notes.ui.notelist

import androidx.lifecycle.ViewModel
import com.veselovvv.notes.data.Note
import com.veselovvv.notes.data.NoteRepository

class NoteListViewModel : ViewModel() {
    private val noteRepository = NoteRepository.get()
    val noteListLiveData = noteRepository.getNotes()
    fun addNote(note: Note) = noteRepository.addNote(note)
}