package com.kingfu.clok.stopwatch.feature.styles


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.kingfu.clok.stopwatch.feature.styles.StopwatchRGBStyle.RGBVariable.RGBColorCounter
import com.kingfu.clok.stopwatch.feature.styles.StopwatchRGBStyle.RGBVariable.RGBHrColorList
import com.kingfu.clok.stopwatch.feature.styles.StopwatchRGBStyle.RGBVariable.RGBMinColorList
import com.kingfu.clok.stopwatch.feature.styles.StopwatchRGBStyle.RGBVariable.RGBMsColorList
import com.kingfu.clok.stopwatch.feature.styles.StopwatchRGBStyle.RGBVariable.RGBSecColorList
import kotlin.math.sin

class StopwatchRGBStyle {

    object RGBVariable {
        var RGBColorCounter by mutableDoubleStateOf(value = 0.0)
        var RGBHrColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
        var RGBMinColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
        var RGBSecColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
        var RGBMsColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
    }

    fun rgbStyleUpdateColors() {
        val frequency = 0.9
        val phase = 1.5
        val width = 128
        val center = 127
        val hrOffset = 0
        val minOffset = 3
        val secOffset = 6
        val msOffset = 9

        RGBColorCounter = (RGBColorCounter + 0.1) % Int.MAX_VALUE

        for (i in 0 until RGBHrColorList.size) {
            RGBHrColorList[i] =
                (sin(frequency * RGBColorCounter + phase * (i + hrOffset)) * width + center).toInt()
            RGBMinColorList[i] =
                (sin(frequency * RGBColorCounter + phase * (i + minOffset)) * width + center).toInt()
            RGBSecColorList[i] =
                (sin(frequency * RGBColorCounter + phase * (i + secOffset)) * width + center).toInt()
            RGBMsColorList[i] =
                (sin(frequency * RGBColorCounter + phase * (i + msOffset)) * width + center).toInt()
        }
    }

    fun rgbStart(rgbColorList: List<Int>): Color {
        return Color(
            rgbColorList[0],
            rgbColorList[1],
            rgbColorList[2]
        )
    }

    fun rgbEnd(rgbColorList: List<Int>): Color {

        return Color(
            rgbColorList[3],
            rgbColorList[4],
            rgbColorList[5]
        )
    }

}