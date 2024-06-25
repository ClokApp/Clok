package com.kingfu.clok.core


fun timeToMilliseconds(hours: Int, minutes: Int, seconds: Int): Long {
    val hoursInMilliseconds = hours.toLong() * 60 * 60 * 1000
    val minutesInMilliseconds = minutes.toLong() * 60 * 1000
    val secondsInMilliseconds = seconds.toLong() * 1000

    return hoursInMilliseconds + minutesInMilliseconds + secondsInMilliseconds
}


