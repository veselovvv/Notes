package com.veselovvv.notes.data

import java.util.UUID
import java.util.concurrent.Executors

class NoteRepository(database: NoteDatabase) {
    private val noteDao = database.noteDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getNotes() = noteDao.getNotes()
    fun getFavoriteNotes() = noteDao.getFavoriteNotes()
    fun getNote(id: UUID) = noteDao.getNote(id)
    fun updateNote(note: Note) = executor.execute { noteDao.updateNote(note) }
    fun addNote(note: Note) = executor.execute { noteDao.addNote(note) }
    fun deleteNote(note: Note) = executor.execute { noteDao.deleteNote(note) }
}