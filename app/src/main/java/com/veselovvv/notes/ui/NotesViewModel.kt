package com.veselovvv.notes.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.veselovvv.notes.data.Note
import com.veselovvv.notes.data.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val noteIdLiveData = MutableLiveData<UUID>()
    val noteListLiveData = noteRepository.getNotes()
    val favoriteNoteListLiveData = noteRepository.getFavoriteNotes()

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