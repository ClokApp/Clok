package com.kingfu.clok.timer.util.timerFontStyle

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun timerFontStyle(
    selectedStyle: TimerFontStyleType,
    miter: Float? = 0f,
    width: Float? = 4.0f,
    join: StrokeJoin? = StrokeJoin.Miter,
    cap: StrokeCap? = StrokeCap.Butt
): DrawStyle? {
    return when (selectedStyle) {
        TimerFontStyleType.InnerStroke -> Stroke(
            miter = miter!!,
            width = width!!,
            join = join!! ,
            cap = cap!!
        )
        TimerFontStyleType.Default -> null
    }
}