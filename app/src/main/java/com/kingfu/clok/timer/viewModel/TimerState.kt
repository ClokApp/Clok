package com.kingfu.clok.timer.viewModel

import java.io.Serializable

data class TimerState(
    val timerTotalTime: Double = 0.0,
    val timerTime: Long = 0L,
    val timerIsFinished: Boolean = false,
    val timerIsActive: Boolean = false,
    val timerHour: Int = 0,
    val timerMinute: Int = 0,
    val timerSecond: Int = 0,
    val timerCurrentTimePercentage: Float = 0.0f,
    val timerIsEditState: Boolean = true,
    val timerInitialTime: Long = 0L,
    val timerOffsetTime: Long = 0L,
    val delay: Long = 55L,
    val isLoadInitialTime: Boolean = true
): Serializable