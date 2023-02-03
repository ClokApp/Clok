package com.kingfu.clok.stopwatch.styles


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle.RGBVariable.RGBColorCounter
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle.RGBVariable.RGBHrColorList
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle.RGBVariable.RGBMinColorList
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle.RGBVariable.RGBMsColorList
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle.RGBVariable.RGBSecColorList
import kotlin.math.sin

class StopwatchRGBStyle {


    object RGBVariable {
        var RGBColorCounter by mutableStateOf(0.0)
        var RGBHrColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
        var RGBMinColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
        var RGBSecColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
        var RGBMsColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
    }

    fun rgbStyleUpdateColors(refreshRate: Float) {
        val frequency = 0.9
        val phase = 1.5
        val width = 128
        val center = 127
        val hrOffset = 0
        val minOffset = 3
        val secOffset = 6
        val msOffset = 9

        val temp: Double =
            when {
                refreshRate <= 25 -> 0.05
                refreshRate <= 50 -> 0.005
                refreshRate <= 75 -> 0.0005
                else -> 0.00005
            }

        RGBColorCounter = (RGBColorCounter + ((refreshRate / 200) + temp)) % Int.MAX_VALUE


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

    fun rgbStyleListRGB(RGBColorList: List<Int>): List<Color> {

        return listOf(
            Color(
                RGBColorList[0],
                RGBColorList[1],
                RGBColorList[2]
            ),
            Color(
                RGBColorList[3],
                RGBColorList[4],
                RGBColorList[5]
            )
        )
    }

}