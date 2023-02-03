package com.kingfu.clok.timer.styles

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.kingfu.clok.timer.styles.TimerRGBStyle.TimerRGBStyleVariable.timerLabelColorList
import com.kingfu.clok.timer.styles.TimerRGBStyle.TimerRGBStyleVariable.timerRGBCounter
import kotlin.math.sin

class TimerRGBStyle {

    object TimerRGBStyleVariable {
        var timerRGBCounter by mutableStateOf(value = 0.0)
        var timerLabelColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
    }

    fun timerUpdateStartAndEndRGB(initialize: Boolean) {

        val frequency = 0.15
        val phase = 1.5
        val width = 128
        val center = 127

        if(!initialize) {
            timerRGBCounter = (timerRGBCounter + 0.4) % Int.MAX_VALUE
        }

        for (i in 0 until timerLabelColorList.size) {
            timerLabelColorList[i] = (sin(frequency * timerRGBCounter + phase * i) * width + center).toInt()
        }
    }


    fun rgbStyleListRGB(): List<Color> {

        return listOf(
            Color(
                timerLabelColorList[0],
                timerLabelColorList[1],
                timerLabelColorList[2],
            ),
            Color(
                timerLabelColorList[3],
                timerLabelColorList[4],
                timerLabelColorList[5],

            )
        )
    }

}