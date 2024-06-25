package com.kingfu.clok.core



fun Long.formatTime(): String {
    val hours = this / 1000 / 60 / 60 % 100
    val minutes = this / 1000 / 60 % 60
    val seconds = this / 1000 % 60
    val milliseconds = this % 1000 / 10

    val hairSpace = "\u200A"

    return if (hours > 0) {
        "%02d${hairSpace}:${hairSpace}%02d${hairSpace}:${hairSpace}%02d${hairSpace}.${hairSpace}%02d".format(
            hours,
            minutes,
            seconds,
            milliseconds
        )
    } else {
        "%02d${hairSpace}:${hairSpace}%02d${hairSpace}.${hairSpace}%02d".format(
            minutes,
            seconds,
            milliseconds
        )
    }
}

fun Long.formatTimeTimer(): String {
    val hours = this / 1000 / 60 / 60 % 100
    val minutes = this / 1000 / 60 % 60
    val seconds = this / 1000 % 60

    val hairSpace = "\u200A"

    return when {
        hours > 10 -> {
            "%02d${hairSpace}:${hairSpace}%02d${hairSpace}:${hairSpace}%02d${hairSpace}".format(
                hours,
                minutes,
                seconds
            )
        }

        hours > 0 -> {
            "%01d${hairSpace}:${hairSpace}%02d${hairSpace}:${hairSpace}%02d${hairSpace}".format(
                hours,
                minutes,
                seconds
            )
        }

        minutes > 10 -> "%02d${hairSpace}:${hairSpace}%02d${hairSpace}".format(minutes, seconds)

        minutes > 0 -> "%01d${hairSpace}:${hairSpace}%02d${hairSpace}".format(minutes, seconds)

        seconds > 10 -> "%02d${hairSpace}".format(seconds)

        else -> "%01d${hairSpace}".format(seconds)

    }
}

