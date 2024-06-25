package com.kingfu.clok.stopwatch.repository.stopwatchRoom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stopwatch_lap_list")
data class StopwatchLapData(
    @PrimaryKey
    @ColumnInfo(name = "lap_number")
    var lapNumber: Int,

    @ColumnInfo(name = "lap_time")
    val lapTime: Long,

    @ColumnInfo(name = "lap_total_time")
    var lapTotalTime: Long,
)

