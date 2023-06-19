package com.kingfu.clok.repository.room.stopwatchRoom

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// Database Access Object
@Dao
interface StopwatchLapDAO {
    @Query("SELECT * FROM stopwatch_lap_list ORDER BY lap_number DESC")
    fun getAll(): Flow<List<StopwatchLapData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(lap: StopwatchLapData)

    @Delete
    suspend fun delete(lap: StopwatchLapData)

    @Update
    suspend fun update(lap: StopwatchLapData)

    @Query("DELETE FROM stopwatch_lap_list")
    suspend fun deleteAllItems()
}