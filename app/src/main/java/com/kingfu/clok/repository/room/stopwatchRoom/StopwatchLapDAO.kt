package com.kingfu.clok.repository.room.stopwatchRoom

import androidx.room.*

// Database Access Object
@Dao
interface StopwatchLapDAO {
    @Query("SELECT * FROM stopwatch_lap_list ORDER BY lap_number DESC")
    suspend fun getAll(): List<StopwatchLapData>

//    @Query("SELECT * from item_list where itemId = :id")
//    fun getById(id:Int): ItemData?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(lap: StopwatchLapData)

    @Delete
    suspend fun delete(lap: StopwatchLapData)

    @Update
    suspend fun update(lap: StopwatchLapData)

    @Query("DELETE FROM stopwatch_lap_list")
    suspend fun deleteAllItems()
}