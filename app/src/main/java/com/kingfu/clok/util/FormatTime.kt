package com.kingfu.clok.util


fun Long.formatTimeMs(format: String = "%02d") = format.format(this % 1000 / 10)

fun Long.formatTimeSec(format: String = "%02d") = format.format(this / 1000 % 60)

fun Long.formatTimeMin(format: String = "%02d") = format.format(this / 1000 / 60 % 60)

fun Long.formatTimeHr(format: String = "%02d") = format.format(this / 1000 / 60 / 60 % 100)


