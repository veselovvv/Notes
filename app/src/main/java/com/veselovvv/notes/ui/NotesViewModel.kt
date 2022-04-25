package com.veselovvv.notes.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.veselovvv.notes.data.Note
import com.veselovvv.notes.data.NoteRepository
import java.util.*

class NotesViewModel : ViewModel() {
    private val noteRepository = NoteRepository.get()
    val noteListLiveData = noteRepository.getNotes()
    private val noteIdLiveData = MutableLiveData<UUID>()

    // Сохранение объекта Note, полученного из БД:
    var noteLiveData = Transformations.switchMap(noteIdLiveData) { noteId ->
        noteRepository.getNote(noteId)
    }

    fun loadNote(noteId: UUID) {
        noteIdLiveData.value = noteId
    }

    fun addNote(note: Note) = noteRepository.addNote(note)
    fun saveNote(note: Note) = noteRepository.updateNote(note)
    fun deleteNote(note: Note) = noteRepository.deleteNote(note)
}