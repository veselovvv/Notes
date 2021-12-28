package com.veselovvv.notes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.veselovvv.notes.models.Note

// База данных (использование Room):
@Database(entities = [ Note::class ], version = 1) // сущность и версия БД
@TypeConverters(NoteTypeConverters::class) // класс с функциями для преобразования типов
abstract class NoteDatabase : RoomDatabase() {

    // Подключение DAO:
    abstract fun noteDao(): NoteDao
}