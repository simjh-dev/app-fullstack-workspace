package com.jh.myownvocabularynotebook.room.viewmodel

data class GameNoteItemExamViewModel(
    val id: Long = -1,
    val noteId: Long = -1,
    val key: String = "",
    val value: String = "",
    val questions: List<String>
)