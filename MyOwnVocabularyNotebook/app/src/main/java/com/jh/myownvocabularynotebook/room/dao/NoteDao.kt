package com.jh.myownvocabularynotebook.room.dao

import androidx.room.*
import com.jh.myownvocabularynotebook.room.entity.Note
import com.jh.myownvocabularynotebook.room.entity.NoteItem

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note")
    fun getNoteAll(): List<Note>

    @Query("SELECT * FROM Note WHERE id = :id")
    fun getNoteById(id: Long): Note

    @Query("SELECT max(id) FROM NoteItem")
    fun getNoteItemMaxId(): Long

    @Query("SELECT * FROM NoteItem WHERE noteId = :noteId")
    fun getNoteItemAllByNoteId(noteId: Long): List<NoteItem>

    @Insert
    fun insertNote(item: Note)

    @Insert
    fun insertNoteItem(item: NoteItem)

    @Insert
    fun insertNoteAll(item: List<Note>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNoteItemAll(item: List<NoteItem>)

    @Update
    fun updateNote(item: Note)

    @Update
    fun updateNoteItem(item: NoteItem)

    @Update
    fun updateNoteAll(item: List<Note>)

    @Update
    fun updateNoteItemAll(item: List<NoteItem>)

    @Delete
    fun deleteNote(item: Note)

    @Delete
    fun deleteNoteItem(item: NoteItem)

    @Delete
    fun deleteNoteAll(item: List<Note>)

    @Delete
    fun deleteNoteItemAll(item: List<NoteItem>)

    @Query("DELETE FROM NoteItem WHERE noteId = :noteId and id not in (:ids)")
    fun deleteNoteItemNotExist(ids: List<Long>, noteId: Long)

}