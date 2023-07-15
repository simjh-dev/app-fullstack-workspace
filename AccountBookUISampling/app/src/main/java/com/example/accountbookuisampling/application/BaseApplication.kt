package com.example.accountbookuisampling.application

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.PrimaryKey
import androidx.room.Room
import com.example.accountbookuisampling.room.AppDataBase
import com.example.accountbookuisampling.room.entity.Asset
import com.example.accountbookuisampling.room.entity.Category
import com.example.accountbookuisampling.room.entity.History
import com.example.accountbookuisampling.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.roundToInt
import kotlin.random.Random

class BaseApplication : Application() {

    private val db by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, DB_NAME).build()
    }

    val assetDao by lazy {
        db.assetDao()
    }

    val categoryDao by lazy {
        db.categoryDao()
    }

    val historyDao by lazy {
        db.historyDao()
    }

    private val TAG = this::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
    }


}