package com.example.accountbookuisampling.main.viewmodel

data class DayViewModel(
    val id: Int,
    val day: String,
    val yearMonth: String,
    val dayOfWeek: String,
    val asset: String,
    val category: String,
    val time: String,
    val type: Int,
    val amount: String
)

