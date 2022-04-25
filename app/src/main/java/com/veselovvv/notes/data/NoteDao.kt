package com.veselovvv.notes.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

// Объект доступа к данным:
@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE id = (:id)")
    fun getNote(id: UUID): LiveData<Note?>

    @Update
    fun updateNote(note: Note)

    @Insert
    fun addNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}