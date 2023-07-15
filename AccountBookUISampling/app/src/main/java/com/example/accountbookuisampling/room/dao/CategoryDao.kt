package com.example.accountbookuisampling.room.dao

import androidx.room.*
import com.example.accountbookuisampling.room.entity.Category

@Dao
interface CategoryDao {

    @Query("SELECT count(*) FROM Category")
    fun getSize(): Int

    @Query("SELECT * FROM Category")
    fun getAll(): List<Category>

    @Query("SELECT * FROM Category WHERE id=:id")
    fun getByid(id: Int): Category

    @Query("SELECT * FROM Category WHERE type=:type")
    fun getByType(type: Int): List<Category>

    @Query("SELECT name FROM Category WHERE id=:id")
    fun getNameByid(id: Int): String

    @Query("SELECT id FROM Category WHERE name=:name")
    fun getIdByName(name: String): Int

    @Insert
    fun insert(category: Category)

    @Insert
    fun insertAll(categories: List<Category>)

    @Update
    fun update(category: Category)

    @Delete
    fun delete(category: Category)
}