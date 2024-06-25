package com.kingfu.clok.stopwatch.viewModel

import java.io.Serializable

data class StopwatchState(
    val isActive: Boolean = false,
    val time: Long = 0L,
    val initialTime: Long = 0L,
    val offsetTime: Long = 0L,
    val isLap: Boolean = false,
    val shortestLapIndex: Int = 0,
    val longestLapIndex: Int = 0
): Serializable



