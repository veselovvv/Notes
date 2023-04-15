package com.veselovvv.notes.data

import android.content.Context
import androidx.room.Room
import java.util.*
import java.util.concurrent.Executors

class NoteRepository private constructor(context: Context) {
    private val database = Room.databaseBuilder(
        context.applicationContext,
        NoteDatabase::class.java,
        DATABASE_NAME
    ).build()
    private val noteDao = database.noteDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getNotes() = noteDao.getNotes()
    fun getFavoriteNotes() = noteDao.getFavoriteNotes()
    fun getNote(id: UUID) = noteDao.getNote(id)
    fun updateNote(note: Note) = executor.execute { noteDao.updateNote(note) }
    fun addNote(note: Note) = executor.execute { noteDao.addNote(note) }
    fun deleteNote(note: Note) = executor.execute { noteDao.deleteNote(note) }

    companion object {
        private const val DATABASE_NAME = "note-database"

        private var instance: NoteRepository? = null

        fun initialize(context: Context) {
            if (instance == null) instance = NoteRepository(context)
        }

        fun get(): NoteRepository =
            instance ?: throw IllegalStateException("NoteRepository is not initialized")
    }
}