package com.jh.myownvocabularynotebook.room.viewmodel

data class NoteItemViewModel(
    val id: Long = -1,
    val noteId: Long = -1,
    var key: String = "",
    var value: String = "",
    var translatedText: String = ""
)