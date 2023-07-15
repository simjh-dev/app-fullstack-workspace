package com.example.accountbookuisampling.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Asset::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("assetId"),
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("categoryId"),
        ),
    ]
)
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val type: Int,
    val date: String,
    val assetId: Int,
    val assetName: String,
    val categoryId: Int,
    val categoryName: String,
    val amount: Int,
    val fee: Int,
    val detail: String?,
    val important: Boolean,
    val additionDetail: String?,
    val image: String?
)
