package com.veselovvv.notes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.veselovvv.notes.models.Note
import com.veselovvv.notes.repositories.NoteRepository
import java.util.*

// Управляет запросом к БД:
class NoteDetailViewModel : ViewModel() {

    private val noteRepository = NoteRepository.get()
    private val noteIdLiveData = MutableLiveData<UUID>()

    // Сохранение объекта Note, полученного из БД:
    var noteLiveData: LiveData<Note?> =
        Transformations.switchMap(noteIdLiveData) { noteId ->
            noteRepository.getNote(noteId)
        }

    fun loadNote(noteId: UUID) {
        noteIdLiveData.value = noteId
    }

    fun saveNote(note: Note) {
        noteRepository.updateNote(note)
    }
}