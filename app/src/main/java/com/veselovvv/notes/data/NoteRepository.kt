package com.veselovvv.notes.data

import android.content.Context
import androidx.room.Room
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "note-database"

class NoteRepository private constructor(context: Context) {
    private val database = Room.databaseBuilder(
        context.applicationContext,
        NoteDatabase::class.java,
        DATABASE_NAME
    ).build()
    private val noteDao = database.noteDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getNotes() = noteDao.getNotes()
    fun getNote(id: UUID) = noteDao.getNote(id)
    fun updateNote(note: Note) = executor.execute { noteDao.updateNote(note) }
    fun addNote(note: Note) = executor.execute { noteDao.addNote(note) }
    fun deleteNote(note: Note) = executor.execute { noteDao.deleteNote(note) }

    companion object {
        private var INSTANCE: NoteRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) INSTANCE = NoteRepository(context)
        }

        fun get(): NoteRepository =
            INSTANCE ?: throw IllegalStateException("NoteRepository is not initialized")
    }
}