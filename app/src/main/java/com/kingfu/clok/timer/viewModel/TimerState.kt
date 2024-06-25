package com.kingfu.clok.timer.viewModel

import java.io.Serializable

data class TimerState(
    val totalTime: Double = 0.0,
    val time: Long = 0L,
    val isFinished: Boolean = false,
    val isActive: Boolean = false,
    val hour: Int = 0,
    val minute: Int = 0,
    val second: Int = 0,
    val isEdit: Boolean = true,
    val initialTime: Long = 0L,
    val offsetTime: Long = 0L,
    val delay: Long = 55L,
    val isLoaded: Boolean = false
): Serializable