package com.jh.myownvocabularynotebook.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val memo: String?,
    val useTranslation: Boolean = false,
    val keyLanguage: String = "",
    val valueLanguage: String = ""
)
