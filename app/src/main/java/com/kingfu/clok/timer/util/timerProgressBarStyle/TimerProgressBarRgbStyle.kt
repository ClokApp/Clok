package com.kingfu.clok.timer.util.timerProgressBarStyle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.kingfu.clok.timer.util.timerProgressBarStyle.TimerProgressBarRgbStyle.TimerProgressBarRgbStyleVariable.timerLabelColorList
import com.kingfu.clok.timer.util.timerProgressBarStyle.TimerProgressBarRgbStyle.TimerProgressBarRgbStyleVariable.timerRgbCounter
import kotlin.math.sin

class TimerProgressBarRgbStyle {

    object TimerProgressBarRgbStyleVariable {
        var timerRgbCounter by mutableDoubleStateOf(value = 0.0)
        var timerLabelColorList = mutableStateListOf(255, 255, 255, 255, 255, 255)
    }

    suspend fun updateTimerProgressBarRgbStyleStartAndEndColor() {

        val frequency = 0.15
        val phase = 1.5
        val width = 128
        val center = 127

        timerRgbCounter = (timerRgbCounter + 0.35) % Int.MAX_VALUE

        for (i in 0 until timerLabelColorList.size) {
            timerLabelColorList[i] =
                (sin(x = frequency * timerRgbCounter + phase * i) * width + center).toInt()
        }
    }


    fun timerProgressBarRgbStyleColorList(): List<Color> {
        return listOf(
            Color(
                timerLabelColorList[0],
                timerLabelColorList[1],
                timerLabelColorList[2]
            ),
            Color(
                timerLabelColorList[3],
                timerLabelColorList[4],
                timerLabelColorList[5]
            )
        )
    }

}