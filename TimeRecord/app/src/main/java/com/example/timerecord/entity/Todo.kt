package com.example.timerecord.entity

data class Todo(
    val id: Long,
    val title: String,
    // "20230331"
    val initDate: String,
    // "0900"
    val startTime: String,
    // "1800"
    val endTime: String,
    // "월,화,수,목,금,토,일" "MON,TUE,WED,THU,FRI,SAT,SUN"
    val repeat: List<Boolean>?,
)
