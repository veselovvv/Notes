package com.veselovvv.notes.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.veselovvv.notes.database.NoteDatabase
import com.veselovvv.notes.models.Note
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "note-database"

// Шаблон репозитория для доступа к БД:
class NoteRepository private constructor(context: Context) {

    // Свойство для хранения ссылки на БД:
    private val database : NoteDatabase = Room.databaseBuilder(
        context.applicationContext,
        NoteDatabase::class.java,
        DATABASE_NAME
    ).build()

    // Свойство для хранения ссылки на объект DAO:
    private val noteDao = database.noteDao()
    // Свойство исполнителя для хранения ссылки:
    private val executor = Executors.newSingleThreadExecutor()

    // Функции для каждой функции в DAO:
    fun getNotes(): LiveData<List<Note>> = noteDao.getNotes()

    fun getNote(id: UUID): LiveData<Note?> = noteDao.getNote(id)

    fun updateNote(note: Note) {
        executor.execute { noteDao.updateNote(note) }
    }

    fun addNote(note: Note) {
        executor.execute { noteDao.addNote(note) }
    }

    companion object {
        private var INSTANCE: NoteRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) INSTANCE = NoteRepository(context)
        }

        fun get(): NoteRepository {
            return INSTANCE ?: throw IllegalStateException("NoteRepository is not initialized")
        }
    }
}