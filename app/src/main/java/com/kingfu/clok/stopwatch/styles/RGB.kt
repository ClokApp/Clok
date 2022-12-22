package com.kingfu.clok.stopwatch.styles


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kingfu.clok.stopwatch.styles.RGB.RGBVariable.RGBColorCounter
import com.kingfu.clok.stopwatch.styles.RGB.RGBVariable.RGBHrColorList
import com.kingfu.clok.stopwatch.styles.RGB.RGBVariable.RGBMinColorList
import com.kingfu.clok.stopwatch.styles.RGB.RGBVariable.RGBMsColorList
import com.kingfu.clok.stopwatch.styles.RGB.RGBVariable.RGBSecColorList
import kotlin.math.sin

class RGB {
    object RGBVariable{
        var RGBColorCounter by mutableStateOf(0.0)
        var RGBHrColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
        var RGBMinColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
        var RGBSecColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
        var RGBMsColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
    }

    fun rgbStyle(refreshRate: Float) {
        val frequency = 0.9
        val phase = 1.5
        val width = 128
        val center = 127
        val hrOffset = 0
        val minOffset = 3
        val secOffset = 6
        val msOffset = 9

        val temp: Double =
            if (refreshRate <= 25) {
                0.025
            } else if (refreshRate > 25 && refreshRate <= 50) {
                0.0025
            } else if (refreshRate > 50 && refreshRate <= 75) {
                0.00025
            } else {
                0.000025
            }

        RGBColorCounter =
            (RGBColorCounter + ((refreshRate / 200) + temp)) % Double.MAX_VALUE
        for (i in 0 until 6) {
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
}