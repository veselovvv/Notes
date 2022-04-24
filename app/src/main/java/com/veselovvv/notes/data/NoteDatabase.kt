package com.veselovvv.notes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// База данных (использование Room):
@Database(entities = [ Note::class ], version = 1) // сущность и версия БД
@TypeConverters(NoteTypeConverters::class) // класс с функциями для преобразования типов
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}