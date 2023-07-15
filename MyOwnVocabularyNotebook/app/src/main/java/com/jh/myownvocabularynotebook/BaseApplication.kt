package com.jh.myownvocabularynotebook

import android.app.Application
import androidx.room.Room
import com.jh.myownvocabularynotebook.room.AppDataBase
import com.jh.myownvocabularynotebook.util.Const.DB_NAME

class BaseApplication : Application() {

    private val db by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    val noteDao by lazy {
        db.noteDao()
    }
}