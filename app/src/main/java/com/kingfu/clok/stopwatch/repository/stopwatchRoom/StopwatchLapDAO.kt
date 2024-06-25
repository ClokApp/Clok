package com.kingfu.clok.stopwatch.repository.stopwatchRoom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// Database Access Object
@Dao
interface StopwatchLapDAO {
    @Query("SELECT * FROM stopwatch_lap_list ORDER BY lap_number ASC")
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