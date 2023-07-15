package com.example.accountbookuisampling.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val type: Int, val name: String, val memo: String?
)
