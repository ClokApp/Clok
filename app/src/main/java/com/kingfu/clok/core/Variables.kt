package com.kingfu.clok.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object Variables {
    var isShowTimerNotification by mutableStateOf(value = false)
}