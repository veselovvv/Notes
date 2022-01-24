package com.veselovvv.notes.database

import androidx.room.TypeConverter
import java.util.*

// Преобразования типов для хранения в базе данных и обратно:
class NoteTypeConverters {
    @TypeConverter
    fun fromDate(date: Date?) = date?.time

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?) = millisSinceEpoch?.let { Date(it) }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? = UUID.fromString(uuid)

    @TypeConverter
    fun fromUUID(uuid: UUID?) = uuid?.toString()
}