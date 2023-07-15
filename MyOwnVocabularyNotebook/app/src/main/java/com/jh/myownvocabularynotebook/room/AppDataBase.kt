package com.jh.myownvocabularynotebook.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jh.myownvocabularynotebook.room.dao.NoteDao
import com.jh.myownvocabularynotebook.room.entity.Note
import com.jh.myownvocabularynotebook.room.entity.NoteItem

@Database(entities = [Note::class, NoteItem::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}