package com.example.timerecord.viewmodel

data class TodoHistoryViewModel(
    val id: Long,
    val todoId: Long,
    // "20230331"
    val targetDate: String,
    // "0900"
    var startTime: String,
    // "1800"
    var endTime: String,
    val viewType: Int,
    val strDayOfWeek: String,
)