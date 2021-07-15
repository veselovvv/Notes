package com.veselovvv.notes

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

// Сущность (использование Room):
@Entity
data class Note(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var title: String = "",
                var text: String = "",
                var date: Date = Date())