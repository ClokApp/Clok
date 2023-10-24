package com.kingfu.clok.util

fun formatLapTime(timeMillis: Long, totalTime: Long): String {
    val hr = timeMillis.formatTimeHr()
    val min = timeMillis.formatTimeMin()
    val sec = timeMillis.formatTimeSec()
    val ms = timeMillis.formatTimeMs()

    return if (totalTime >= 3_600_000) "$hr:$min:$sec.$ms" else "$min:$sec.$ms"
}

fun formatLapTotalTime(totalTime: Long): String {
    val hr = totalTime.formatTimeHr()
    val min = totalTime.formatTimeMin()
    val sec = totalTime.formatTimeSec()
    val ms = totalTime.formatTimeMs()

    return if (totalTime >= 3_600_000) "$hr:$min:$sec.$ms" else "$min:$sec.$ms"
}