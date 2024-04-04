package com.kingfu.clok.stopwatch.viewModel

import java.io.Serializable

data class StopwatchState(
    val stopwatchIsActive: Boolean = false,
    val stopwatchTime: Long = 0L,
    val lapPreviousTime: Long = 0L,
    val stopwatchInitialTime: Long = 0L,
    val stopwatchOffsetTime: Long = 0L,
    val isLap: Boolean = false,
    val shortestLapIndex: Int = 0,
    val longestLapIndex: Int = 0,
    val isScrollLazyColumn: Boolean = false,
    val refreshRate: Float = 55f
): Serializable



