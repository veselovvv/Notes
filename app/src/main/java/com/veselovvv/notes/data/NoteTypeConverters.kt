package com.veselovvv.notes.data

import androidx.room.TypeConverter
import java.util.*

// Преобразования типов для хранения в базе данных и обратно:
class NoteTypeConverters {
    @TypeConverter
    fun toUUID(uuid: String?): UUID? = UUID.fromString(uuid)

    @TypeConverter
    fun fromUUID(uuid: UUID?) = uuid?.toString()
}