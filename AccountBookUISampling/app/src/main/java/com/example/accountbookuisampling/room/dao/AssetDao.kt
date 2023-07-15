package com.example.accountbookuisampling.room.dao

import androidx.room.*
import com.example.accountbookuisampling.room.entity.Asset

@Dao
interface AssetDao {

    @Query("SELECT count(*) FROM Asset")
    fun getSize(): Int

    @Query("SELECT * FROM Asset")
    fun getAll(): List<Asset>

    @Query("SELECT * FROM Asset WHERE id=:id")
    fun getByid(id: Int): Asset

    @Query("SELECT * FROM Asset WHERE type=:type")
    fun getByType(type: Int): List<Asset>

    @Query("SELECT name FROM Asset WHERE id=:id")
    fun getNameByid(id: Int): String

    @Query("SELECT id FROM Asset WHERE name=:name")
    fun getIdByName(name: String): Int

    @Insert
    fun insert(asset: Asset)

    @Insert
    fun insertAll(assets: List<Asset>)

    @Update
    fun update(asset: Asset)

    @Delete
    fun delete(asset: Asset)

}