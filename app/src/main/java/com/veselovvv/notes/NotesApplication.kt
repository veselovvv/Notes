package com.veselovvv.notes

import android.app.Application
import com.veselovvv.notes.repositories.NoteRepository

// Позволяет получить информацию о жизненном цикле приложения:
class NotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NoteRepository.initialize(this)
    }
}