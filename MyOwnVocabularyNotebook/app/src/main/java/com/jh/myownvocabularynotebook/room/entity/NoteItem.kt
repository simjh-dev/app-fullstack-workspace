package com.jh.myownvocabularynotebook.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = -1,
    val noteId: Long = -1,
    var key: String = "",
    var value: String = ""
)