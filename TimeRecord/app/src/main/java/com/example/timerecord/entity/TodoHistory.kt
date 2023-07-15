package com.example.timerecord.entity

data class TodoHistory(
    val id: Long,
    val todoId: Long,
    // "20230331"
    val targetDate: String,
    // "0900"
    val startTime: String,
    // "1800"
    val endTime: String
)