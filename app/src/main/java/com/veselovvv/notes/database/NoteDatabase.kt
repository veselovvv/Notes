package com.veselovvv.notes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.veselovvv.notes.Note

// База данных (использование Room):
@Database(entities = [ Note::class ], version = 1) // указываем сущность и версию БД
@TypeConverters(NoteTypeConverters::class) // указываем класс с функциями для преобразования типов
abstract class NoteDatabase : RoomDatabase() {

    // Подключение DAO:
    abstract fun noteDao(): NoteDao
}