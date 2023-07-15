package com.example.accountbookuisampling.room.dao

import androidx.annotation.Nullable
import androidx.room.*
import com.example.accountbookuisampling.room.dto.Summary
import com.example.accountbookuisampling.room.entity.History

@Dao
interface HistoryDao {

    @Query("SELECT * FROM History")
    fun getAll(): List<History>

    @Query("SELECT * FROM History WHERE id=:id")
    fun getByid(id: Int): History

    @Query("SELECT * FROM History WHERE date like :date || '%'")
    fun getByDate(date: String?): List<History>

    @Query("SELECT type, sum(amount + fee) as result, substr(date, 1, 6) as date FROM HISTORY WHERE substr(date, 1, 4) = :year group by type, substr(date, 1, 6) order by substr(date, 1, 6)")
    fun getSummaryByYear(year: String?): List<Summary>

    @Query("SELECT type, sum(amount + fee) as result FROM HISTORY WHERE date like :date || '%' group by type")
    fun getSummaryByDate(date: String?): List<Summary>

    @Query("SELECT type, sum(amount + fee) as result, date FROM HISTORY WHERE :firstDate <= substr(date, 1, 8) and substr(date, 1, 8) <= :lastDate group by type, substr(date, 1, 8) order by substr(date, 1, 8)")
    fun getSummaryByPeriod(firstDate: String, lastDate: String): List<Summary>

    @Insert
    fun insert(history: History)

    @Insert
    fun insertAll(histories: List<History>)

    @Update
    fun update(history: History)

    @Delete
    fun delete(history: History)

}