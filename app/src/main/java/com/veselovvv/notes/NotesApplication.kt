package com.veselovvv.notes

import android.app.Application

// Позволяет получить информацию о жизненном цикле приложения:
class NotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        NoteRepository.initialize(this)
    }
}